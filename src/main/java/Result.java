package com.exammanagement.model;

import java.io.Serializable;

/**
 * Result model class representing an exam result in the system
 */
public class Result implements Serializable {
    private String id;
    private String studentId;
    private String examId;
    private int score;
    private String grade;
    private String submissionDate;

    // Additional transient variables for display purposes
    private transient String studentName;
    private transient String examTitle;

    // Constructor
    public Result() {
    }

    public Result(String id, String studentId, String examId, int score, String grade, String submissionDate) {
        this.id = id;
        this.studentId = studentId;
        this.examId = examId;
        this.score = score;
        this.grade = grade;
        this.submissionDate = submissionDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    @Override
    public String toString() {
        return id + "," + studentId + "," + examId + "," + score + "," + grade + "," + submissionDate;
    }

    // Parse from string (used when reading from file)
    public static Result fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length == 6) {
            return new Result(
                    parts[0],
                    parts[1],
                    parts[2],
                    Integer.parseInt(parts[3]),
                    parts[4],
                    parts[5]);
        }
        return null;
    }
}
