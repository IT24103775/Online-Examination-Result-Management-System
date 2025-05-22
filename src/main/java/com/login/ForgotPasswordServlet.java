package com.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    //create
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password"); // Only one parameter available

        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            out.println("<script>alert('Missing fields.'); window.location='forgotPassword.jsp';</script>");
            return;
        }

        String filePath = getServletContext().getRealPath("user.txt");
        File file = new File(filePath);

        if (!file.exists()) {
            out.println("<script>alert('User file not found.'); window.location='forgotPassword.jsp';</script>");
            return;
        }

        List<String> updatedRecords = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Email: ")) {
                    String storedEmail = line.substring(7).trim();
                    String phoneLine = reader.readLine(); // Assume present
                    String passwordLine = reader.readLine(); // Assume present

                    if (storedEmail.equals(email)) {
                        updatedRecords.add("Email: " + storedEmail);
                        updatedRecords.add(phoneLine);
                        updatedRecords.add("Password: " + password);
                        updated = true;
                    } else {
                        updatedRecords.add("Email: " + storedEmail);
                        if (phoneLine != null) updatedRecords.add(phoneLine);
                        if (passwordLine != null) updatedRecords.add(passwordLine);
                    }
                } else {
                    updatedRecords.add(line); // Safety catch for unexpected formats
                }
            }
        }

        if (updated) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (String record : updatedRecords) {
                    writer.println(record);
                }
            }
            out.println("<script>alert('Password updated successfully!'); window.location='index.jsp';</script>");
        } else {
            out.println("<script>alert('Email not found!'); window.location='forgotPassword.jsp';</script>");
        }
    }
}