package com.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/signup")
public class signUpServelet extends HttpServlet {

    // constructor
    public signUpServelet() {

    }

    // create record
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");


        String filePath = getServletContext().getRealPath("/Login/data/user.txt");

        int recordNumber = 1;

        // find last record
        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int lastNumber = 0;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Record #:")) {
                        try {
                            lastNumber = Integer.parseInt(line.split(":")[1].trim());
                        } catch (NumberFormatException e) {
                            e.printStackTrace(); // handle errors
                        }
                    }
                }
                recordNumber = lastNumber + 1; // increment
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Write the new record with a record number
        try (PrintWriter fileWriter = new PrintWriter(new java.io.FileWriter(filePath, true))) {
            fileWriter.println("Record #: " + recordNumber);
            fileWriter.println("First Name: " + firstName);
            fileWriter.println("Last Name: " + lastName);
            fileWriter.println("Email: " + email);
            fileWriter.println("Phone: " + phone);
            fileWriter.println("Password: " + password);
            fileWriter.println("---------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Send response
        response.sendRedirect("index.jsp");
    }

    // Get User list and single record
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filePath = getFilePath(request);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        File file = new File(filePath);
        if (!file.exists()) {
            out.println("No users found.");
            return;
        }

        String recordIdParam = request.getParameter("recordId");

        // Check if a recordId is provided in the request
        if (recordIdParam != null) {
            // Fetch a single record based on recordId
            int recordId = Integer.parseInt(recordIdParam);
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean recordFound = false;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Record #: " + recordId)) {
                        recordFound = true;
                        out.println(line); // Print Record # line
                        out.println(reader.readLine()); // First Name
                        out.println(reader.readLine()); // Last Name
                        out.println(reader.readLine()); // Email
                        out.println(reader.readLine()); // Phone
                        out.println(reader.readLine()); // Password
                        out.println("---------------------------");
                        break;
                    }
                }
                if (!recordFound) {
                    out.println("Record not found for Record ID: " + recordId);
                }
            }
        } else {
            // If no recordId is provided, return all records
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    out.println(line); // Print Record # line
                    // Only print the following lines if they exist (to avoid null values)
                    String firstName = reader.readLine();
                    String lastName = reader.readLine();
                    String email = reader.readLine();
                    String phone = reader.readLine();
                    String password = reader.readLine();

                    if (firstName != null) out.println(firstName);
                    if (lastName != null) out.println(lastName);
                    if (email != null) out.println(email);
                    if (phone != null) out.println(phone);
                    if (password != null) out.println(password);

                    out.println("---------------------------");
                }
            }
        }
    }

    // 📌 Update User
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recordIdStr = request.getParameter("recordId");

        // check nulls
        if (recordIdStr == null || recordIdStr.isEmpty()) {
            response.getWriter().println("Error: Missing or invalid recordId.");
            return;
        }

        int recordId;
        try {
            recordId = Integer.parseInt(recordIdStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("Error: Invalid recordId format.");
            return;
        }

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        String filePath = getFilePath(request);
        File file = new File(filePath);
        if (!file.exists()) {
            response.getWriter().println("Error: File not found.");
            return;
        }

        List<String> updatedRecords = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Record #: " + recordId)) {
                    updatedRecords.add("Record #: " + recordId);
                    updatedRecords.add("First Name: " + firstName);
                    updatedRecords.add("Last Name: " + lastName);
                    updatedRecords.add("Email: " + email);
                    updatedRecords.add("Phone: " + phone);
                    updatedRecords.add("Password: " + password);
                    updated = true;
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                } else {
                    updatedRecords.add(line);
                }
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String rec : updatedRecords) {
                writer.println(rec);
            }
        }

        response.getWriter().println(updated ? "User updated successfully!" : "Record not found.");
    }

    // 📌 Delete User
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recordId = Integer.parseInt(request.getParameter("recordId"));

        String filePath = getFilePath(request);
        File file = new File(filePath);
        if (!file.exists()) {
            response.getWriter().println("Error: File not found.");
            return;
        }

        List<String> remainingRecords = new ArrayList<>();
        boolean deleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Record #: " + recordId)) {
                    deleted = true;
                    for (int i = 0; i < 6; i++) {
                        reader.readLine();
                    }
                    continue;
                }
                remainingRecords.add(line);
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String rec : remainingRecords) {
                writer.println(rec);
            }
        }

        response.getWriter().println(deleted ? "User deleted successfully!" : "Record not found.");
    }

    // get file path
    private String getFilePath(HttpServletRequest request) {
        return getServletContext().getRealPath("/Login/data/user.txt");
    }

}
