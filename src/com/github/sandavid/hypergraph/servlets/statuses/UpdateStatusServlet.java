package com.github.sandavid.hypergraph.servlets.statuses;

import org.json.JSONObject;

import com.github.sandavid.hypergraph.services.StatusesServices;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class UpdateStatusServlet
 */
public class UpdateStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String token = request.getParameter("token");
        String text = request.getParameter("text");
        JSONObject json = StatusesServices.UpdateStatus(token, text);
        out.print(json);
    }

}
