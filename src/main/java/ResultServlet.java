package com.exammanagement.controller;

import com.exammanagement.model.Exam;
import com.exammanagement.model.Result;
import com.exammanagement.model.Student;
import com.exammanagement.service.ExamService;
import com.exammanagement.service.ResultService;
import com.exammanagement.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling result management
 */
public class ResultServlet extends HttpServlet {
    private ResultService resultService;
    private StudentService studentService;
    private ExamService examService;

    @Override
    public void init() throws ServletException {
        resultService = new ResultService();
        studentService = new StudentService();
        examService = new ExamService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteResult(request, response);
                break;
            case "view":
                viewResult(request, response);
                break;
            case "sortByScore":
                sortResultsByScore(request, response);
                break;
            case "sortByName":
                sortResultsByStudentName(request, response);
                break;
            default:
                listResults(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                createResult(request, response);
                break;
            case "update":
                updateResult(request, response);
                break;
            default:
                listResults(request, response);
                break;
        }
    }

    private void listResults(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Result> results = resultService.getAllResults();
        request.setAttribute("results", results);
        request.getRequestDispatcher("/WEB-INF/view/admin/result_list.jsp").forward(request, response);
    }

    private void sortResultsByScore(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String order = request.getParameter("order");
        boolean ascending = "asc".equals(order);

        List<Result> results = resultService.getSortedResultsByScore(ascending);

        request.setAttribute("results", results);
        request.setAttribute("sortBy", "score");
        request.setAttribute("order", ascending ? "asc" : "desc");
        request.getRequestDispatcher("/WEB-INF/view/admin/result_list.jsp").forward(request, response);
    }

    private void sortResultsByStudentName(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String order = request.getParameter("order");
        boolean ascending = "asc".equals(order);

        List<Result> results = resultService.getSortedResultsByStudentName(ascending);

        request.setAttribute("results", results);
        request.setAttribute("sortBy", "name");
        request.setAttribute("order", ascending ? "asc" : "desc");
        request.getRequestDispatcher("/WEB-INF/view/admin/result_list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> students = studentService.getAllStudents();
        List<Exam> exams = examService.getAllExams();

        request.setAttribute("students", students);
        request.setAttribute("exams", exams);
        request.getRequestDispatcher("/WEB-INF/view/admin/result_form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Result result = resultService.getResultById(id);

        if (result == null) {
            // Result not found
            request.setAttribute("errorMessage", "Result not found");
            listResults(request, response);
            return;
        }

        List<Student> students = studentService.getAllStudents();
        List<Exam> exams = examService.getAllExams();

        request.setAttribute("students", students);
        request.setAttribute("exams", exams);
        request.setAttribute("result", result);
        request.getRequestDispatcher("/WEB-INF/view/admin/result_form.jsp").forward(request, response);
    }

    private void createResult(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String studentId = request.getParameter("studentId");
        String examId = request.getParameter("examId");
        int score = Integer.parseInt(request.getParameter("score"));
        String submissionDate = request.getParameter("submissionDate");

        Result result = new Result();
        result.setStudentId(studentId);
        result.setExamId(examId);
        result.setScore(score);
        result.setSubmissionDate(submissionDate);

        resultService.addResult(result);

        response.sendRedirect(request.getContextPath() + "/admin/results");
    }

    private void updateResult(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String id = request.getParameter("id");
        String studentId = request.getParameter("studentId");
        String examId = request.getParameter("examId");
        int score = Integer.parseInt(request.getParameter("score"));
        String submissionDate = request.getParameter("submissionDate");

        Result result = resultService.getResultById(id);
        if (result == null) {
            response.sendRedirect(request.getContextPath() + "/admin/results");
            return;
        }

        result.setStudentId(studentId);
        result.setExamId(examId);
        result.setScore(score);
        result.setSubmissionDate(submissionDate);

        boolean updated = resultService.updateResult(result);

        if (updated) {
            response.sendRedirect(request.getContextPath() + "/admin/results");
        } else {
            request.setAttribute("errorMessage", "Failed to update result");

            List<Student> students = studentService.getAllStudents();
            List<Exam> exams = examService.getAllExams();

            request.setAttribute("students", students);
            request.setAttribute("exams", exams);
            request.setAttribute("result", result);
            request.getRequestDispatcher("/WEB-INF/view/admin/result_form.jsp").forward(request, response);
        }
    }

    private void deleteResult(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");

        resultService.deleteResult(id);

        response.sendRedirect(request.getContextPath() + "/admin/results");
    }

    private void viewResult(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Result result = resultService.getResultById(id);

        if (result == null) {
            // Result not found
            request.setAttribute("errorMessage", "Result not found");
            listResults(request, response);
            return;
        }

        request.setAttribute("result", result);
        request.getRequestDispatcher("/WEB-INF/view/admin/result_view.jsp").forward(request, response);
    }
}
