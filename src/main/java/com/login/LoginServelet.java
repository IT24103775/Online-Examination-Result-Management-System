package com.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServelet extends HttpServlet {

    public LoginServelet() {}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember");

        // Validate credentials
        boolean isValidUser = validateUser(username, password);

        if (isValidUser) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Set session timeout based on remember me
            if (rememberMe != null && rememberMe.equals("on")) {
                session.setMaxInactiveInterval(30 * 24 * 60 * 60); // 30 days
            } else {
                session.setMaxInactiveInterval(30 * 60); // 30 minutes
            }

            // Redirect to welcome page
            response.sendRedirect("signUp.jsp");
        } else {
            // Send error response
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Login Failed</title></head>");
            out.println("<body><h1>Login Failed: Invalid username or password</h1>");
            out.println("<a href='index.jsp'>Try again</a></body></html>");
        }
    }

    private boolean validateUser(String username, String password) {
        String filePath = getServletContext().getRealPath("/Login/data/user.txt");
        File file = new File(filePath);

        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Map<String, String> currentUser = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Record #:")) {
                    currentUser.clear(); // Start new record
                } else if (line.startsWith("Email: ")) {
                    currentUser.put("email", line.substring("Email: ".length()).trim());
                } else if (line.startsWith("Password: ")) {
                    currentUser.put("password", line.substring("Password: ".length()).trim());
                } else if (line.equals("---------------------------")) {
                    // End of record - check if credentials match
                    if (currentUser.get("email") != null &&
                            currentUser.get("email").equals(username) &&
                            currentUser.get("password") != null &&
                            currentUser.get("password").equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}