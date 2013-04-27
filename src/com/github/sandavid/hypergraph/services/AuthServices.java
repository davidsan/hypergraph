package com.github.sandavid.hypergraph.services;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.sandavid.hypergraph.bd.AuthTools;
import com.github.sandavid.hypergraph.bd.BDException;
import com.github.sandavid.hypergraph.bd.UsersTools;
import com.github.sandavid.hypergraph.servlets.tools.ServicesTools;


public class AuthServices {
    public static JSONObject login(String username, String password) {
        if ((username == null) || (password == null)) {
            return ServicesTools.error400();
        }
        try {
            if (!UsersTools.userExists(username)) {
                return ServicesTools.error("Account Not Found", 1);
            }
            if (!AuthTools.verifyCredentials(username, password)) {
                return ServicesTools.error401();
            }
            String token = AuthTools.generateToken();
            int id_user = AuthTools.getUserId(username);
            AuthTools.insertSession(token, id_user, false);
            JSONObject res = new JSONObject();
            try {
                res.put("id", id_user);
                res.put("username", username);
                res.put("token", token);
            } catch (JSONException e) {
                return ServicesTools.error999();
            }
            return res;
        } catch (BDException e) {
            return ServicesTools.error999();
        }

    }

    public static JSONObject logout(String token) {
        if (token == null) {
            return ServicesTools.error400();
        }
        try {
            if (!AuthTools.isActiveSession(token)) {
                return ServicesTools.error401();
            }

            AuthTools.deleteSession(token);
            return ServicesTools.ok();
        } catch (BDException e) {
            return ServicesTools.error999();
        }

    }
}
