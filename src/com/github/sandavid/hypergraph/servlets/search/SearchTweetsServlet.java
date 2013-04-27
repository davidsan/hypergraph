package com.github.sandavid.hypergraph.servlets.search;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.github.sandavid.hypergraph.services.SearchServices;



/**
 * Servlet implementation class SearchTweetsServlet
 */

public class SearchTweetsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		String token = request.getParameter("token");
		String query = request.getParameter("query");
		String friends = request.getParameter("friends");
		JSONObject json = SearchServices.SearchTweets(token, query, friends);
		out.print(json);
	}
}
