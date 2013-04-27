package com.github.sandavid.hypergraph.servlets;

import org.json.JSONObject;

import com.github.sandavid.hypergraph.bd.Database;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        JSONObject json = new JSONObject();
        String username = "moi";
        String password = "pwd";
        String name = "truc";
        try {
            Connection connection = Database.getMySQLConnection();
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO users (username, password, name) VALUES (?, ?, ?);");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        out.print(json);
    }
}
