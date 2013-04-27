package com.github.sandavid.hypergraph.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class UsersTools {

	public static boolean userExists(String username) throws BDException {
		boolean b = false;
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM users WHERE username=?;");
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				b = true;
			} else {
				b = false;
			}
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
		return b;
	}

	public static boolean userExistsId(String id) throws BDException {
		boolean b = false;
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM users WHERE id=?;");
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				b = true;
			} else {
				b = false;
			}
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
		return b;
	}

	public static void createUser(String username, String password, String name)
			throws BDException {
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO users (username, password, name) VALUES (?, ?, ?);");
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, name);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
	}

	public static HashMap<String, Integer> showUser(int uid) {
		HashMap<String, Integer> hash = new HashMap<String, Integer>();

		String[] attributs = { "statuses_count", "followers_count",
				"friends_count" };
		try {
			Connection connection = Database.getMySQLConnection();
			String strAttribs = Arrays.toString(attributs).replaceAll(
					"[\\[\\]]", "");
			PreparedStatement statement = connection.prepareStatement("SELECT "
					+ strAttribs + " FROM users WHERE id=?;");
			statement.setLong(1, uid);
			ResultSet rs = statement.executeQuery();
			rs.next();
			for (int i = 0; i < attributs.length; i++) {
				hash.put(attributs[i], rs.getInt(attributs[i]));
			}

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hash;
	}

	private static void incrementCount(int uid, String field)
			throws BDException {
		Connection connection;
		try {
			connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("UPDATE users SET " + field + "=" + field
							+ "+1 WHERE id=?;");
			statement.setLong(1, uid);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new BDException();
		}
	}

	private static void decrementCount(int uid, String field)
			throws BDException {
		Connection connection;
		try {
			connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("UPDATE users SET " + field + "=" + field
							+ "-1 WHERE id=?;");
			statement.setLong(1, uid);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new BDException();
		}
	}

	public static void incrementStatusesCount(String token) throws BDException {
		int uid = AuthTools.getUserIdFromToken(token);
		incrementCount(uid, "statuses_count");
	}

	public static void decrementStatusesCount(String token) throws BDException {
		int uid = AuthTools.getUserIdFromToken(token);
		decrementCount(uid, "statuses_count");
	}

	public static void incrementFriendsCount(int requestorId)
			throws BDException {
		incrementCount(requestorId, "friends_count");
	}

	public static void incrementFollowersCount(int requesteeId)
			throws BDException {
		incrementCount(requesteeId, "followers_count");
	}

	public static void decrementFriendsCount(int requestorId)
			throws BDException {
		decrementCount(requestorId, "friends_count");
	}

	public static void decrementFollowersCount(int requesteeId)
			throws BDException {
		decrementCount(requesteeId, "followers_count");
	}

	public static String getName(int id) throws BDException {
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT name FROM users WHERE id=?;");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} 
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
		return "";
	}

}
