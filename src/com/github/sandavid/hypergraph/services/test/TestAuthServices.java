package com.github.sandavid.hypergraph.services.test;

import com.github.sandavid.hypergraph.services.AuthServices;

public class TestAuthServices {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(AuthServices.login("johndoe", "1234"));
	}

}
