package com.github.sandavid.hypergraph.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class AuthTools {

	public static boolean verifyCredentials(String username, String password)
			throws BDException {
		boolean b = false;
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM users WHERE username=? && password=?;");
			statement.setString(1, username);
			statement.setString(2, password);
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

	public static String generateToken() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static Boolean validateToken(String token) {
		return !(token == null || token.length() != 32);
	}

	public static void insertSession(String token, int id_user, boolean bool)
			throws BDException {
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO sessions (token, id_user) VALUES (?, ?);");
			statement.setString(1, token);
			statement.setInt(2, id_user);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
	}

	public static void deleteSession(String token) throws BDException {
		try {
			Connection connection = Database.getMySQLConnection();

			PreparedStatement statement = connection
					.prepareStatement("UPDATE sessions SET outdated=1 WHERE token=?;");
			statement.setString(1, token);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
	}

	public static boolean sessionExists(String token) throws BDException {
		boolean b = false;
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM sessions WHERE token=?;");
			statement.setString(1, token);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				b = true;
			}
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
		return b;
	}

	public static boolean isActiveSession(String token) throws BDException {
		boolean b = false;
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM sessions WHERE token=? && outdated=0;");
			statement.setString(1, token);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				b = true;
			}
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
		return b;
	}

	public static int getUserId(String username) throws BDException {
		int id = -1;
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT id FROM users WHERE username=?;");
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			rs.next();
			id = rs.getInt(1);
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
		return id;
	}

	public static int getUserIdFromToken(String token) throws BDException {
		int id = -1;
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT id_user FROM sessions WHERE token=?");
			statement.setString(1, token);
			ResultSet rs = statement.executeQuery();
			rs.next();
			id = rs.getInt(1);
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
		return id;
	}

	public static String getUsernameFromId(String id) throws BDException {
		String login = "";
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT username FROM users WHERE id=?;");
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery();
			rs.next();
			login = rs.getString(1);
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
		return login;
	}

	public static String getUsernameFromId(int id) throws BDException {
		String login = "";
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT username FROM users WHERE id=?;");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			rs.next();
			login = rs.getString(1);
			statement.close();
			connection.close();
		} catch (Exception e) {
			throw new BDException();
		}
		return login;
	}
}
