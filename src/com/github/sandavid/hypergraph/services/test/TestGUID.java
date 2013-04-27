package com.github.sandavid.hypergraph.services.test;

import java.util.UUID;

public class TestGUID {

	public static void main(String[] args) {
		final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println("uuid = " + uuid);
	}

}
