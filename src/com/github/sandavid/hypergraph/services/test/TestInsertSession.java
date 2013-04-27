package com.github.sandavid.hypergraph.services.test;

import com.github.sandavid.hypergraph.bd.AuthTools;

public class TestInsertSession {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AuthTools.insertSession("1ad01823a4734f1682e1b4debcc677ce", 0,
					false);
			AuthTools.deleteSession("1ad01823a4734f1682e1b4debcc677ce");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
