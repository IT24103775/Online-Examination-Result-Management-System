package com.exam.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exam.model.User;
import com.exam.util.FileHandler;

// Changed URL pattern to avoid conflict
@WebServlet("/userManage")
public class UserManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward requests to the user management view page
        request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            // Add a new user
            addUser(request, response);
        } else if ("update".equals(action)) {
            // Update an existing user
            updateUser(request, response);
        } else if ("delete".equals(action)) {
            // Delete a user
            deleteUser(request, response);
        } else {
            request.setAttribute("errorMessage", "Invalid action specified");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
        }
    }
    
    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        
        if (username == null || username.isEmpty() || 
            password == null || password.isEmpty() || 
            email == null || email.isEmpty() ||
            role == null || role.isEmpty()) {
            
            request.setAttribute("errorMessage", "All fields are required");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
            return;
        }
        
        // Check if username already exists
        boolean userExists = FileHandler.getAllUsers().stream()
                .anyMatch(u -> u.getUsername().equals(username));
                
        if (userExists) {
            request.setAttribute("errorMessage", "Username already exists");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
            return;
        }
        
        User newUser = new User(username, password, email, role);
        
        if (FileHandler.saveUser(newUser)) {
            request.setAttribute("successMessage", "User added successfully");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Failed to add user");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
        }
    }
    
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String newUsername = request.getParameter("newUsername");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        
        if (username == null || username.isEmpty() ||
            newUsername == null || newUsername.isEmpty() ||
            email == null || email.isEmpty() ||
            role == null || role.isEmpty()) {
            
            request.setAttribute("errorMessage", "Required fields are missing");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
            return;
        }
        
        // Get the current user
        User currentUser = null;
        for (User user : FileHandler.getAllUsers()) {
            if (user.getUsername().equals(username)) {
                currentUser = user;
                break;
            }
        }
        
        if (currentUser == null) {
            request.setAttribute("errorMessage", "User not found");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
            return;
        }
        
        // Check if the username is being changed and if new username already exists
        if (!username.equals(newUsername)) {
            boolean usernameExists = FileHandler.getAllUsers().stream()
                    .anyMatch(u -> u.getUsername().equals(newUsername));
                    
            if (usernameExists) {
                request.setAttribute("errorMessage", "Username already exists");
                request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
                return;
            }
        }
        
        // Update user details
        if (password != null && !password.isEmpty()) {
            currentUser.setPassword(password);
        }
        
        currentUser.setUsername(newUsername);
        currentUser.setEmail(email);
        currentUser.setRole(role);
        
        if (FileHandler.updateUser(username, currentUser)) {
            request.setAttribute("successMessage", "User updated successfully");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Failed to update user");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
        }
    }
    
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        
        if (username == null || username.isEmpty()) {
            request.setAttribute("errorMessage", "Username is required");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
            return;
        }
        
        // Make sure we're only deleting the specific user with this username
        if (FileHandler.deleteUser(username)) {
            request.setAttribute("successMessage", "User deleted successfully");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Failed to delete user");
            request.getRequestDispatcher("user-management-view.jsp").forward(request, response);
        }
    }
}
