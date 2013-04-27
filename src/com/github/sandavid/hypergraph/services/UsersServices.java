package com.github.sandavid.hypergraph.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.sandavid.hypergraph.bd.AuthTools;
import com.github.sandavid.hypergraph.bd.BDException;
import com.github.sandavid.hypergraph.bd.Database;
import com.github.sandavid.hypergraph.bd.UsersTools;
import com.github.sandavid.hypergraph.servlets.tools.ServicesTools;

public class UsersServices {
	public static JSONObject createUser(String username, String password,
			String name) {
		if ((username == null) || (password == null) || (name == null)) {
			return ServicesTools.error400();
		}
		try {
			if (UsersTools.userExists(username)) {
				return ServicesTools.error("Login Already Taken", 1);
			}
			UsersTools.createUser(username, password, name);
			return ServicesTools.ok();
		} catch (BDException e) {
			return ServicesTools.error999();
		}
	}

	public static JSONObject showUser(String token) {
		if (token == null) {
			return ServicesTools.error400();
		}
		try {
			if (!AuthTools.isActiveSession(token)) {
				return ServicesTools.error401();
			}
			int uid = AuthTools.getUserIdFromToken(token);
			HashMap<String, Integer> hash = UsersTools.showUser(uid);
			int statusesCount = (hash.get("statuses_count"));
			int followersCount = (hash.get("followers_count"));
			int friendsCount = (hash.get("friends_count"));
			JSONObject res = new JSONObject();
			try {
				res.put("id", uid);
				res.put("statuses_count", statusesCount);
				res.put("followers_count", followersCount);
				res.put("friends_count", friendsCount);

			} catch (JSONException e) {
				return ServicesTools.error999();
			}
			return res;
		} catch (BDException e) {
			return ServicesTools.error999();
		}
	}

	public static JSONObject suggestUser(String token) {
		if (token == null) {
			return ServicesTools.error400();
		}
		try {
			if (!AuthTools.isActiveSession(token)) {
				return ServicesTools.error401();
			}
			int uid = AuthTools.getUserIdFromToken(token);
			JSONObject res = new JSONObject();
			try {
				Connection connection = Database.getMySQLConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM users WHERE id!=? AND NOT EXISTS (SELECT * FROM friendships WHERE requestorId=? AND requesteeId=users.id);");
				statement.setInt(1, uid);
				statement.setInt(2, uid);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					JSONObject usr = new JSONObject();
					usr.put("id", rs.getInt("id"));
					usr.put("id_str", rs.getString("id"));
					usr.put("username", rs.getString("username"));
					usr.put("name", rs.getString("name"));
					res.accumulate("users", usr);
				}

				statement.close();
				connection.close();
			} catch (Exception e) {
				throw new BDException();
			}
			return res;
		} catch (BDException e) {
			return ServicesTools.error999();
		}
	}

}
