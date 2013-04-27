package com.github.sandavid.hypergraph.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendshipsTools {
	public static void createFriendship(int requestorId, int requesteeId)
			throws BDException {
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO friendships (requestorId, requesteeId) VALUES (?, ?);");
			statement.setInt(1, requestorId);
			statement.setInt(2, requesteeId);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean existsFriendship(int requestorId, int requesteeId) {
		boolean b = false;
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM friendships WHERE requestorId=? && requesteeId=?;");
			statement.setInt(1, requestorId);
			statement.setInt(2, requesteeId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				b = true;
			} else {
				b = false;
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public static void destroyFriendship(int requestorId, int requesteeId) {
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM friendships WHERE requestorId=? && requesteeId=?;");
			statement.setInt(1, requestorId);
			statement.setInt(2, requesteeId);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> findFriendship(int requestorId) {
		ArrayList<String> friends = new ArrayList<String>();
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM friendships WHERE requestorId=?;");
			statement.setInt(1, requestorId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				friends.add(rs.getString("requesteeId"));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friends;
	}

}
