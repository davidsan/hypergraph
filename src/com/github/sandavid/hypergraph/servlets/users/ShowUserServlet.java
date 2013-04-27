package com.github.sandavid.hypergraph.servlets.users;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.github.sandavid.hypergraph.services.UsersServices;

/**
 * Servlet implementation class ShowUserServlet
 */

public class ShowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String token = request.getParameter("token");
        JSONObject json = UsersServices.showUser(token);
        out.print(json);
    }


}
