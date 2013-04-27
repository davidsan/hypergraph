package com.github.sandavid.hypergraph.servlets.friendships;

import org.json.JSONObject;

import com.github.sandavid.hypergraph.services.FriendshipsServices;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class DestroyFriendshipServlet
 */
public class DestroyFriendshipServlet extends HttpServlet {
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
        String requesteeId = request.getParameter("requesteeId");
        JSONObject json = FriendshipsServices.destroyFriendship(token,
                requesteeId);
        out.print(json);
    }

}