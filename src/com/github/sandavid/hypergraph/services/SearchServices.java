package com.github.sandavid.hypergraph.services;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import com.github.sandavid.hypergraph.bd.AuthTools;
import com.github.sandavid.hypergraph.bd.BDException;
import com.github.sandavid.hypergraph.bd.Database;
import com.github.sandavid.hypergraph.bd.FriendshipsTools;
import com.github.sandavid.hypergraph.servlets.tools.ServicesTools;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public class SearchServices {
	public static JSONObject SearchTweets(String token, String query,
			String friends) {
		if (friends == null) {
			return ServicesTools.error400();
		}
		JSONObject json = new JSONObject();

		try {
			int onlyFriends = Integer.parseInt(friends);
			if (onlyFriends == 1) {
				if (token == null || !AuthTools.validateToken(token)
						|| (!AuthTools.sessionExists(token))
						|| (!AuthTools.isActiveSession(token))) {
					return ServicesTools.error400();
				}
				if (query == null || query.equals("")) {
					return SearchTweetsFromFriends(token);
				} else {
					// !TODO MAP REDUCE QUERY RESTRICTED ON FRIENDS

				}
			} else {
				if (query == null || query.equals("")) {
					if (token == null || token == ""
							|| AuthTools.validateToken(token)) {
						return SearchAllTweets(token);
					} else {
						return ServicesTools.error400();
					}
				} else {
					// !TODO MAP REDUCE QUERY

				}
			}
		} catch (BDException e) {
			return ServicesTools.error999();
		}
		return json;
	}

	public static JSONObject SearchAllTweets(String token) throws BDException {
		JSONObject json = new JSONObject();
		DBCollection coll = Database.getMongoCollection("messages");
		BasicDBObject query = new BasicDBObject();
		DBCursor o = coll.find(query).sort(new BasicDBObject("$natural", -1));
		ArrayList<String> arFriends = new ArrayList<String>();
		if (token != null && token != "") {
			int requestorId = AuthTools.getUserIdFromToken(token);
			arFriends = FriendshipsTools.findFriendship(requestorId);
		}
		try {
			while (o.hasNext()) {
				DBObject dbo = o.next();
				DBObject dboCustom = new BasicDBObject();

				dboCustom.put("_id", dbo.get("_id"));
				dboCustom.put("text", dbo.get("text"));

				DBObject dblAuthor = new BasicDBObject();
				dblAuthor.put("author_username", dbo.get("author_username"));
				dblAuthor.put("author_name", dbo.get("author_name"));
				dblAuthor.put("author_id", dbo.get("author_id"));
				dblAuthor.put("author_id_str", dbo.get("author_id_str"));
				boolean contact = false;

				if (token != null && token != "") {
					contact = arFriends.contains(dbo.get("author_id_str"));
				}
				dblAuthor.put("contact", contact);
				dboCustom.put("author", dblAuthor);
				dboCustom.put("created_at", dbo.get("created_at"));
				dboCustom.put("score", 0);
				json.accumulate("results", dboCustom);
			}
			json.put("query", "");
			json.put("contacts_only", 0);
			json.put("author", "");
			Date d = new Date();
			json.put("date", d.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BDException();
		}

		return json;
	}

	public static JSONObject SearchTweetsFromFriends(String token)
			throws BDException {
		JSONObject json = new JSONObject();
		DBCollection coll = Database.getMongoCollection("messages");
		int requestorId = AuthTools.getUserIdFromToken(token);
		ArrayList<String> arFriends = FriendshipsTools
				.findFriendship(requestorId);
		arFriends.add("" + requestorId);
		BasicDBList docIds = new BasicDBList();
		docIds.addAll(arFriends);
		DBObject inClause = new BasicDBObject("$in", docIds);
		DBObject query = new BasicDBObject("author_id_str", inClause);

		DBCursor o = coll.find(query).sort(new BasicDBObject("$natural", -1));
		try {
			while (o.hasNext()) {
				DBObject dbo = o.next();
				DBObject dboCustom = new BasicDBObject();

				dboCustom.put("_id", dbo.get("_id"));
				dboCustom.put("text", dbo.get("text"));
				String strUID = (String) dbo.get("author_id_str");
				boolean contact = arFriends.contains(strUID);
				DBObject dblAuthor = new BasicDBObject();
				dblAuthor.put("author_username", dbo.get("author_username"));
				dblAuthor.put("author_name", dbo.get("author_name"));
				dblAuthor.put("author_id", dbo.get("author_id"));
				dblAuthor.put("author_id_str", dbo.get("author_id_str"));
				dblAuthor.put("contact", contact);
				dboCustom.put("author", dblAuthor);
				dboCustom.put("created_at", dbo.get("created_at"));
				dboCustom.put("score", 0);
				json.accumulate("results", dboCustom);

			}
			json.put("query", "");
			json.put("contacts_only", 1);
			json.put("author", requestorId);
			json.put("date", new Date().getTime());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BDException();
		}
		return json;
	}

	public static JSONObject SearchTweetsFromUser(String token, String id)
			throws BDException {
		JSONObject json = new JSONObject();
		DBCollection coll = Database.getMongoCollection("messages");
		QueryBuilder qb = new QueryBuilder();
		qb.put("author_id_str").is(id);
		boolean contact = false;
		int requestorId = -1;
		if (AuthTools.validateToken(token) && AuthTools.sessionExists(token)
				&& AuthTools.isActiveSession(token)) {
			requestorId = AuthTools.getUserIdFromToken(token);
			contact = FriendshipsTools.existsFriendship(requestorId,
					Integer.parseInt(id));
		}
		DBCursor o = coll.find(qb.get())
				.sort(new BasicDBObject("$natural", -1));
		;
		try {
			while (o.hasNext()) {
				DBObject dbo = o.next();
				DBObject dboCustom = new BasicDBObject();

				dboCustom.put("_id", dbo.get("_id"));
				dboCustom.put("text", dbo.get("text"));
				DBObject dblAuthor = new BasicDBObject();
				dblAuthor.put("author_username", dbo.get("author_username"));
				dblAuthor.put("author_name", dbo.get("author_name"));
				dblAuthor.put("author_id", dbo.get("author_id"));
				dblAuthor.put("author_id_str", dbo.get("author_id_str"));
				dblAuthor.put("contact", contact);
				dboCustom.put("author", dblAuthor);
				dboCustom.put("created_at", dbo.get("created_at"));
				dboCustom.put("score", 0);
				json.accumulate("results", dboCustom);
			}
			json.put("query", "");
			json.put("contacts_only", 1);
			json.put("author", requestorId);
			json.put("date", new Date().getTime());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BDException();
		}
		return json;
	}

	public static JSONObject ListStatus(String token, String uid) {
		JSONObject json = new JSONObject();

		try {
			if (token == null) {
				return ServicesTools.error400();
			}

			json = SearchTweetsFromUser(token, uid);
		} catch (BDException e) {
			return ServicesTools.error999();
		}
		return json;
	}
}
