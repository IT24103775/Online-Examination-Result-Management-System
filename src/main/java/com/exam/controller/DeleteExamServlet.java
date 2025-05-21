package com.exam.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession

import com.exam.util.ExamFileHandler;

@WebServlet("/deleteExam")
public class DeleteExamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        
        // Only admin can delete exams
        if (!"admin".equals(role)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String examId = request.getParameter("examId");
        
        if (examId != null && !examId.isEmpty()) {
            // Delete only the exam and its questions, not affecting student data
            boolean deleted = ExamFileHandler.deleteExam(examId);
            
            if (deleted) {
                request.setAttribute("successMessage", "Exam deleted successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to delete exam.");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid exam ID.");
        }
        
        // Redirect to view-exams.jsp to show the current list
        request.getRequestDispatcher("view-exams.jsp").forward(request, response);
    }
}
