package com.exam.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exam.model.Exam;
import com.exam.util.ExamFileHandler;

@WebServlet("/examDetails")
public class ExamDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        
        // Only admin can view exam details
        if (!"admin".equals(role)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String examId = request.getParameter("examId");
        
        if (examId != null && !examId.isEmpty()) {
            Exam exam = ExamFileHandler.getExamById(examId);
            
            if (exam != null) {
                request.setAttribute("exam", exam);
                request.getRequestDispatcher("exam-details.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Exam not found.");
                request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Invalid exam ID.");
            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
        }
    }
}
