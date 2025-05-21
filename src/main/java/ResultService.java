package com.exammanagement.service;

import com.exammanagement.model.Exam;
import com.exammanagement.model.Result;
import com.exammanagement.model.Student;
import com.exammanagement.util.FileHandler;
import com.exammanagement.util.SelectionSort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service class for Result-related operations
 */
public class ResultService {
    private static final String RESULTS_FILE = "results.txt";

    private final StudentService studentService;
    private final ExamService examService;

    public ResultService() {
        studentService = new StudentService();
        examService = new ExamService();
    }

    /**
     * Get all results with student and exam information
     * 
     * @return List of all results
     */
    public List<Result> getAllResults() {
        try {
            List<Result> results = FileHandler.readFromFile(
                    FileHandler.getFilePath(RESULTS_FILE),
                    Result::fromString);

            // Populate student and exam information
            populateResultsInfo(results);

            return results;
        } catch (IOException e) {
            System.err.println("Error loading results: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Get a result by ID
     * 
     * @param id The result ID
     * @return The result, or null if not found
     */
    public Result getResultById(String id) {
        try {
            List<Result> results = FileHandler.readFromFile(
                    FileHandler.getFilePath(RESULTS_FILE),
                    Result::fromString);

            for (Result result : results) {
                if (result.getId().equals(id)) {
                    // Populate student and exam information
                    populateResultInfo(result);
                    return result;
                }
            }

            return null;
        } catch (IOException e) {
            System.err.println("Error loading results: " + e.getMessage());
            return null;
        }
    }

    /**
     * Add a new result
     * 
     * @param result The result to add
     * @return The added result with generated ID
     */
    public Result addResult(Result result) {
        try {
            List<Result> results = FileHandler.readFromFile(
                    FileHandler.getFilePath(RESULTS_FILE),
                    Result::fromString);

            // Generate a unique ID if not provided
            if (result.getId() == null || result.getId().isEmpty()) {
                result.setId(UUID.randomUUID().toString().substring(0, 8));
            }

            // Calculate grade based on score
            if (result.getGrade() == null || result.getGrade().isEmpty()) {
                result.setGrade(calculateGrade(result.getScore()));
            }

            results.add(result);

            FileHandler.writeToFile(results, FileHandler.getFilePath(RESULTS_FILE));

            // Populate student and exam information
            populateResultInfo(result);

            return result;
        } catch (IOException e) {
            System.err.println("Error adding result: " + e.getMessage());
            return null;
        }
    }

    /**
     * Update an existing result
     * 
     * @param result The result to update
     * @return true if updated, false if not found
     */
    public boolean updateResult(Result result) {
        try {
            List<Result> results = FileHandler.readFromFile(
                    FileHandler.getFilePath(RESULTS_FILE),
                    Result::fromString);

            // Calculate grade based on score if not provided
            if (result.getGrade() == null || result.getGrade().isEmpty()) {
                result.setGrade(calculateGrade(result.getScore()));
            }

            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getId().equals(result.getId())) {
                    results.set(i, result);
                    FileHandler.writeToFile(results, FileHandler.getFilePath(RESULTS_FILE));

                    // Populate student and exam information
                    populateResultInfo(result);

                    return true;
                }
            }

            return false;
        } catch (IOException e) {
            System.err.println("Error updating result: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a result by ID
     * 
     * @param id The result ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteResult(String id) {
        try {
            List<Result> results = FileHandler.readFromFile(
                    FileHandler.getFilePath(RESULTS_FILE),
                    Result::fromString);

            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getId().equals(id)) {
                    results.remove(i);
                    FileHandler.writeToFile(results, FileHandler.getFilePath(RESULTS_FILE));
                    return true;
                }
            }

            return false;
        } catch (IOException e) {
            System.err.println("Error deleting result: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get sorted results using selection sort
     * 
     * @param ascending If true, sort in ascending order; otherwise, descending
     *                  order
     * @return List of sorted results
     */
    public List<Result> getSortedResultsByScore(boolean ascending) {
        List<Result> results = getAllResults();
        SelectionSort.sortByScore(results, ascending);
        return results;
    }

    /**
     * Get sorted results by student name using selection sort
     * 
     * @param ascending If true, sort in ascending order; otherwise, descending
     *                  order
     * @return List of sorted results
     */
    public List<Result> getSortedResultsByStudentName(boolean ascending) {
        List<Result> results = getAllResults();
        SelectionSort.sortByStudentName(results, ascending);
        return results;
    }

    /**
     * Get results for a specific student
     * 
     * @param studentId The student ID
     * @return List of results for the student
     */
    public List<Result> getResultsByStudentId(String studentId) {
        List<Result> allResults = getAllResults();
        List<Result> studentResults = new ArrayList<>();

        for (Result result : allResults) {
            if (result.getStudentId().equals(studentId)) {
                studentResults.add(result);
            }
        }

        return studentResults;
    }

    /**
     * Get results for a specific exam
     * 
     * @param examId The exam ID
     * @return List of results for the exam
     */
    public List<Result> getResultsByExamId(String examId) {
        List<Result> allResults = getAllResults();
        List<Result> examResults = new ArrayList<>();

        for (Result result : allResults) {
            if (result.getExamId().equals(examId)) {
                examResults.add(result);
            }
        }

        return examResults;
    }

    /**
     * Populate student and exam information for a list of results
     * 
     * @param results The list of results to populate
     */
    private void populateResultsInfo(List<Result> results) {
        for (Result result : results) {
            populateResultInfo(result);
        }
    }

    /**
     * Populate student and exam information for a single result
     * 
     * @param result The result to populate
     */
    private void populateResultInfo(Result result) {
        // Get student information
        Student student = studentService.getStudentById(result.getStudentId());
        if (student != null) {
            result.setStudentName(student.getName());
        } else {
            result.setStudentName("Unknown Student");
        }

        // Get exam information
        Exam exam = examService.getExamById(result.getExamId());
        if (exam != null) {
            result.setExamTitle(exam.getTitle());
        } else {
            result.setExamTitle("Unknown Exam");
        }
    }

    /**
     * Calculate grade based on score
     * 
     * @param score The score to calculate grade for
     * @return The calculated grade
     */
    private String calculateGrade(int score) {
        if (score >= 90) {
            return "A+";
        } else if (score >= 80) {
            return "A";
        } else if (score >= 70) {
            return "B";
        } else if (score >= 60) {
            return "C";
        } else if (score >= 50) {
            return "D";
        } else {
            return "F";
        }
    }
}
