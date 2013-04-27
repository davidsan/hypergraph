package com.github.sandavid.hypergraph.services;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.sandavid.hypergraph.bd.AuthTools;
import com.github.sandavid.hypergraph.bd.BDException;
import com.github.sandavid.hypergraph.bd.Database;
import com.github.sandavid.hypergraph.bd.MongoTools;
import com.github.sandavid.hypergraph.bd.UsersTools;
import com.github.sandavid.hypergraph.servlets.tools.ServicesTools;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class StatusesServices {
	public static JSONObject UpdateStatus(String token, String text) {
		if ((token == null) || (text == null)) {
			return ServicesTools.error400();
		}
		try {
			if (!AuthTools.sessionExists(token)
					|| !AuthTools.isActiveSession(token)) {
				return ServicesTools.error400();
			}
			int user_id = AuthTools.getUserIdFromToken(token);
			String login = AuthTools.getUsernameFromId(user_id);
			DBCollection coll = Database.getMongoCollection("messages");
			BasicDBObject query = new BasicDBObject();
			query.put("token", token);
			query.put("text", text);
			query.put("author_id", user_id);
			query.put("author_id_str", "" + user_id);
			query.put("author_username", login);
			String name=UsersTools.getName(user_id);
			query.put("author_name", name);
			query.put("created_at", new Date().getTime());
			coll.insert(query);
			UsersTools.incrementStatusesCount(token);
			return ServicesTools.ok();
		} catch (BDException e) {
			return ServicesTools.error999();
		}
	}

	public static JSONObject ShowStatus(String id) {
		if (id == null) {
			return ServicesTools.error400();
		}
		JSONObject json = new JSONObject();
		try {
			DBCollection coll = Database.getMongoCollection("messages");
			DBObject o = MongoTools.findOneById(coll, id);
			if (o == null) {
				return ServicesTools.error403();
			}
			json.put("result", o);
		} catch (JSONException e) {
			return ServicesTools.error999();
		}
		return json;
	}

	public static JSONObject DestroyStatus(String token, String id) {
		if (token == null || id == null) {
			return ServicesTools.error400();
		}
		try {
			if ((!AuthTools.sessionExists(token))
					|| (!AuthTools.isActiveSession(token))) {
				return ServicesTools.error403();
			}
		} catch (BDException e) {
			return ServicesTools.error999();
		}
		DBCollection coll = Database.getMongoCollection("messages");
		DBObject o = MongoTools.findOneById(coll, id);
		if (o == null) {
			return ServicesTools.error403();
		}
		Object oUID = o.get("author_id");
		int iUID = (Integer) oUID;
		try {
			if (iUID != AuthTools.getUserIdFromToken(token)) {
				return ServicesTools.error403();
			}
		} catch (BDException e) {
			return ServicesTools.error999();
		}
		coll.remove(o);

		try {
			UsersTools.decrementStatusesCount(token);
		} catch (BDException e) {
			return ServicesTools.error999();
		}
		return ServicesTools.ok();
	}

	// ! TODO Servlet associé (combiné avec ShowUser, à voir?) + doc
	// Fonction pas utile ?
	public static JSONObject ShowStatuses(String uid) {
		JSONObject json = new JSONObject();
		DBCollection coll = Database.getMongoCollection("messages");
		BasicDBObject query = new BasicDBObject();
		int iUID = Integer.parseInt(uid);
		query.put("author_id", iUID);
		DBCursor o = coll.find(query);
		try {
			while (o.hasNext()) {
				json.accumulate("results", o.next());
			}
		} catch (JSONException e) {
			return ServicesTools.error999();
		}

		return json;
	}

}
