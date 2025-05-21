package com.exammanagement.controller;

import com.exammanagement.model.Answer;
import com.exammanagement.model.Exam;
import com.exammanagement.model.Question;
import com.exammanagement.model.Result;
import com.exammanagement.model.User;
import com.exammanagement.model.Student;
import com.exammanagement.service.AnswerService;
import com.exammanagement.service.ExamService;
import com.exammanagement.service.QuestionService;
import com.exammanagement.service.ResultService;
import com.exammanagement.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Servlet for handling student exam actions (view, take, submit)
 */
public class StudentExamServlet extends HttpServlet {
    private ExamService examService;
    private QuestionService questionService;
    private ResultService resultService;
    private StudentService studentService;
    private AnswerService answerService;

    @Override
    public void init() throws ServletException {
        examService = new ExamService();
        questionService = new QuestionService();
        resultService = new ResultService();
        studentService = new StudentService();
        answerService = new AnswerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if student is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        Student student = studentService.getStudentById(user.getStudentId());

        if (student == null) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "take":
                showExam(request, response);
                break;
            case "submit":
                submitExam(request, response);
                break;
            default:
                listExams(request, response, student);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // For exam submission
        String action = request.getParameter("action");

        if ("submit".equals(action)) {
            submitExam(request, response);
        } else {
            doGet(request, response);
        }
    }

    private void listExams(HttpServletRequest request, HttpServletResponse response, Student student)
            throws ServletException, IOException {
        // Get all available exams
        List<Exam> exams = examService.getAllExams();

        // Get student's already completed exams to mark them as completed
        List<Result> results = resultService.getResultsByStudentId(student.getId());
        Map<String, Result> completedExams = new HashMap<>();

        for (Result result : results) {
            completedExams.put(result.getExamId(), result);
        }

        request.setAttribute("exams", exams);
        request.setAttribute("completedExams", completedExams);
        request.setAttribute("student", student);

        request.getRequestDispatcher("/WEB-INF/view/user/exams.jsp").forward(request, response);
    }

    private void showExam(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        String examId = request.getParameter("id");
        if (examId == null || examId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user/exams");
            return;
        }

        // Check if student has already completed this exam
        List<Result> results = resultService.getResultsByStudentId(user.getStudentId());
        for (Result result : results) {
            if (result.getExamId().equals(examId)) {
                // Student already completed this exam
                request.setAttribute("message",
                        "You have already completed this exam. Your score: " + result.getScore());
                request.getRequestDispatcher("/WEB-INF/view/user/exams.jsp").forward(request, response);
                return;
            }
        }

        Exam exam = examService.getExamById(examId);
        if (exam == null) {
            response.sendRedirect(request.getContextPath() + "/user/exams");
            return;
        }

        // Get exam questions
        List<Question> questions = questionService.getQuestionsByExamId(examId);

        // Start the exam timer
        session.setAttribute("examStartTime", System.currentTimeMillis());

        request.setAttribute("exam", exam);
        request.setAttribute("questions", questions);

        request.getRequestDispatcher("/WEB-INF/view/user/take_exam.jsp").forward(request, response);
    }

    private void submitExam(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String examId = request.getParameter("examId");

        if (examId == null || examId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user/exams");
            return;
        }

        // Check if the exam time is over
        Long startTime = (Long) session.getAttribute("examStartTime");
        if (startTime == null) {
            response.sendRedirect(request.getContextPath() + "/user/exams");
            return;
        }

        Exam exam = examService.getExamById(examId);
        if (exam == null) {
            response.sendRedirect(request.getContextPath() + "/user/exams");
            return;
        }

        // Check if time's up
        long currentTime = System.currentTimeMillis();
        long elapsedMinutes = (currentTime - startTime) / (1000 * 60);

        if (elapsedMinutes > exam.getDuration()) {
            request.setAttribute("error", "Time's up! Your answers were submitted automatically.");
        }

        // Process exam submission
        List<Question> questions = questionService.getQuestionsByExamId(examId);
        int totalMarks = 0;
        int scoredMarks = 0;

        // Save each answer to the file
        List<Answer> answers = new ArrayList<>();
        String submissionTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

        for (Question question : questions) {
            totalMarks += question.getMarks();

            String answer = request.getParameter("answer_" + question.getId());

            // Create and save the answer
            Answer answerObj = new Answer();
            answerObj.setStudentId(user.getStudentId());
            answerObj.setExamId(examId);
            answerObj.setQuestionId(question.getId());
            answerObj.setAnswerText(answer != null ? answer : "");
            answerObj.setSubmissionTime(submissionTime);

            answers.add(answerObj);

            if (answer != null && !answer.isEmpty()) {
                if ("multiple-choice".equals(question.getType())) {
                    // For MCQs, check if the answer matches the correct option
                    if (answer.equals(question.getCorrectAnswer())) {
                        scoredMarks += question.getMarks();
                    }
                } else if ("essay".equals(question.getType())) {
                    // For essays, we can't automatically grade, so assume half marks
                    // In a real system, these would be manually graded later
                    scoredMarks += (question.getMarks() / 2);
                }
            }
        }

        // Save all answers to file
        answerService.addAllAnswers(answers);

        // Calculate score as a percentage
        int score = (totalMarks > 0) ? (scoredMarks * 100) / totalMarks : 0;

        // Calculate grade
        String grade;
        if (score >= 90)
            grade = "A+";
        else if (score >= 80)
            grade = "A";
        else if (score >= 70)
            grade = "B+";
        else if (score >= 60)
            grade = "B";
        else if (score >= 50)
            grade = "C";
        else if (score >= 40)
            grade = "D";
        else
            grade = "F";

        // Save the result
        Result result = new Result();
        result.setId("RES" + UUID.randomUUID().toString().substring(0, 8));
        result.setStudentId(user.getStudentId());
        result.setExamId(examId);
        result.setExamTitle(exam.getTitle());
        result.setScore(score);
        result.setGrade(grade);
        result.setSubmissionDate(submissionTime);

        resultService.addResult(result);
        System.out.println("Result saved to file: " + result.toString());

        // Clear the exam timer from session
        session.removeAttribute("examStartTime");

        // Show results
        request.setAttribute("result", result);
        request.setAttribute("message", "Exam submitted successfully!");
        request.getRequestDispatcher("/WEB-INF/view/user/exam_result.jsp").forward(request, response);
    }
}
