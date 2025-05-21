package com.exam.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exam.model.Exam;
import com.exam.util.ExamFileHandler;

@WebServlet("/exam")
public class ExamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        
        // Only admin can add exams
        if (!"admin".equals(role)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("createExam".equals(action)) {
            createExam(request, response);
        }
    }
    
    private void createExam(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String moduleName = request.getParameter("moduleName");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int duration = Integer.parseInt(request.getParameter("duration"));
        
        Exam exam = new Exam(UUID.randomUUID().toString(), moduleName, title, description, duration);
        
        // Save the exam
        if (ExamFileHandler.saveExam(exam)) {
            request.setAttribute("successMessage", "Exam created successfully! You can view it in the exams list.");
            // Stay on admin dashboard instead of redirecting to view-exams.jsp
            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Failed to create exam. Please try again.");
            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
        }
    }
}
