package com.github.sandavid.hypergraph.servlets.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class ServicesTools {
    public static JSONObject error(String error, int code) {
        JSONObject json = new JSONObject();
        try {
            json.put("error", error);
            json.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject ok() {
        JSONObject json = new JSONObject();
        return json;
    }

    public static JSONObject error400() {
        return ServicesTools.error("Bad Request", 400);
    }

    public static JSONObject error401() {
        return ServicesTools.error("Unauthorized", 401);
    }

    public static JSONObject error403() {
        return ServicesTools.error("Forbidden", 403);
    }

    public static JSONObject error409() {
        return ServicesTools.error("AccountAlreadyExists", 409);
    }

    public static JSONObject error420() {
        return ServicesTools.error("Enhance Your Calm", 420);
    }

    public static JSONObject error501() {
        return ServicesTools.error("Not Implemented", 501);
    }

    public static JSONObject error999() {
        return ServicesTools.error("Unexpected Error", 999);
    }
}
