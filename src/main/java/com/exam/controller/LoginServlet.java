package com.exam.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Only handle admin login
        if ("admin".equals(username) && "admin1".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", "admin");
            response.sendRedirect("admin-dashboard.jsp");
        } else {
            // Redirect to login page with error for non-admin attempts
            request.setAttribute("errorMessage", "Invalid admin credentials");
            request.getRequestDispatcher("admin-login.jsp").forward(request, response);
        }
    }
}
