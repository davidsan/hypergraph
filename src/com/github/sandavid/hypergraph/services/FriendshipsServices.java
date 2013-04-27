package com.github.sandavid.hypergraph.services;

import org.json.JSONObject;

import com.github.sandavid.hypergraph.bd.AuthTools;
import com.github.sandavid.hypergraph.bd.BDException;
import com.github.sandavid.hypergraph.bd.FriendshipsTools;
import com.github.sandavid.hypergraph.bd.UsersTools;
import com.github.sandavid.hypergraph.servlets.tools.ServicesTools;

public class FriendshipsServices {

	public static JSONObject createFriendship(String token,
			String strRequesteeId) {
		// Vérifier args non nul
		if (token == null || strRequesteeId == null) {
			return ServicesTools.error400();
		}
		int requesteeId = Integer.parseInt(strRequesteeId);

		try {
			// Vérifier que le requesteeId existe dans les users
			if (!UsersTools.userExistsId(strRequesteeId)) {
				return ServicesTools.error400();
			}
			// Vérifier que le token existe dans les sessions
			if (!AuthTools.sessionExists(token)) {
				return ServicesTools.error400();
			}
			// Récupérer l'id du token dans requestorId
			int requestorId = AuthTools.getUserIdFromToken(token);
			// Vérifier que l'on ne se follow pas soi-même
			if (requestorId == requesteeId) {
				return ServicesTools.error400();
			}
			// Vérifier que la relation n'existe pas déjà
			if (FriendshipsTools.existsFriendship(requestorId, requesteeId)) {
				return ServicesTools.error403();
			}
			// Ajouter la relation dans la bd
			FriendshipsTools.createFriendship(requestorId, requesteeId);
			UsersTools.incrementFriendsCount(requestorId);
			UsersTools.incrementFollowersCount(requesteeId);
		} catch (BDException e) {
			return ServicesTools.error999();
		}
		// Renvoyer JSON OK
		return ServicesTools.ok();
	}

	public static JSONObject destroyFriendship(String token,
			String strRequesteeId) {
		// Vérifier args non nul
		if (token == null || strRequesteeId == null) {
			return ServicesTools.error400();
		}
		int requesteeId = Integer.parseInt(strRequesteeId);

		try {
			// Vérifier que le requesteeId existe dans les users
			if (!UsersTools.userExistsId(strRequesteeId)) {
				return ServicesTools.error400();
			}
			// Vérifier que le token existe dans les sessions
			if (!AuthTools.sessionExists(token)) {
				return ServicesTools.error400();
			}
			// Récupérer l'id du token dans requestorId
			int requestorId = AuthTools.getUserIdFromToken(token);
			// Vérifier que l'on ne se follow pas soi-même
			if (requestorId == requesteeId) {
				return ServicesTools.error400();
			}
			// Vérifier que la relation existe pour pouvoir la détruire
			if (!FriendshipsTools.existsFriendship(requestorId, requesteeId)) {
				return ServicesTools.error400();
			}
			// Supprimer la relation dans la bd
			FriendshipsTools.destroyFriendship(requestorId, requesteeId);
			UsersTools.decrementFriendsCount(requestorId);
			UsersTools.decrementFollowersCount(requesteeId);
		} catch (BDException e) {
			return ServicesTools.error999();
		}
		// Renvoyer JSON OK
		return ServicesTools.ok();
	}
}
