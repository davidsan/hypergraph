package com.github.sandavid.hypergraph.services.test;

import org.json.JSONObject;

import com.github.sandavid.hypergraph.services.UsersServices;



public class TestUserServices {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JSONObject json = UsersServices.createUser("SuperSan", null,
				"David San");
		System.out.println(json);
		JSONObject jsonOk = UsersServices.createUser("SuperSan", "toto",
				"David San");
		System.out.println(jsonOk);
	}

}
