package com.exam.util;

import com.exam.model.Exam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExamFileHandler {
    private static String examFilePath = "/WEB-INF/data/exam.txt";
    private static String realExamFilePath;

    /**
     * Saves an exam to the file
     */
    public static boolean saveExam(Exam exam) {
        ensureDirectoryExists();

        if (exam.getId() == null || exam.getId().isEmpty()) {
            exam.setId(UUID.randomUUID().toString());
        }

        try (FileWriter fw = new FileWriter(realExamFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println("[EXAM]");
            out.println("ID: " + exam.getId());
            out.println("Module: " + exam.getModuleName());
            out.println("Title: " + exam.getTitle());
            out.println("Description: " + exam.getDescription());
            out.println("Duration: " + exam.getDuration());
            out.println("[/EXAM]");
            out.println();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets all exams from the file
     */
    public static List<Exam> getAllExams() {
        List<Exam> exams = new ArrayList<>();

        File file = new File(realExamFilePath);
        if (!file.exists()) {
            ensureDirectoryExists();
            return exams;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(realExamFilePath))) {
            String line;
            Exam currentExam = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.equals("[EXAM]")) {
                    currentExam = new Exam();
                } else if (line.startsWith("ID: ")) {
                    if (currentExam != null) {
                        currentExam.setId(line.substring("ID: ".length()));
                    }
                } else if (line.startsWith("Module: ")) {
                    if (currentExam != null) {
                        currentExam.setModuleName(line.substring("Module: ".length()));
                    }
                } else if (line.startsWith("Title: ")) {
                    if (currentExam != null) {
                        currentExam.setTitle(line.substring("Title: ".length()));
                    }
                } else if (line.startsWith("Description: ")) {
                    if (currentExam != null) {
                        currentExam.setDescription(line.substring("Description: ".length()));
                    }
                } else if (line.startsWith("Duration: ")) {
                    if (currentExam != null) {
                        try {
                            currentExam.setDuration(Integer.parseInt(line.substring("Duration: ".length())));
                        } catch (NumberFormatException e) {
                            currentExam.setDuration(60); // Default duration
                        }
                    }
                } else if (line.equals("[/EXAM]")) {
                    if (currentExam != null) {
                        exams.add(currentExam);
                        currentExam = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exams;
    }

    /**
     * Finds an exam by its ID
     */
    public static Exam getExamById(String examId) {
        List<Exam> exams = getAllExams();

        for (Exam exam : exams) {
            if (exam.getId().equals(examId)) {
                return exam;
            }
        }

        return null;
    }

    /**
     * Gets all exams for a specific module
     */
    public static List<Exam> getExamsByModule(String moduleName) {
        List<Exam> exams = getAllExams();
        List<Exam> moduleExams = new ArrayList<>();

        for (Exam exam : exams) {
            if (exam.getModuleName().equals(moduleName)) {
                moduleExams.add(exam);
            }
        }

        return moduleExams;
    }

    /**
     * Deletes an exam by ID
     */
    public static boolean deleteExam(String examId) {
        if (examId == null || examId.isEmpty()) {
            return false;
        }

        List<Exam> exams = getAllExams();
        boolean removed = false;

        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).getId().equals(examId)) {
                exams.remove(i);
                removed = true;
                break;
            }
        }

        if (removed) {
            try (FileWriter fw = new FileWriter(realExamFilePath, false);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                for (Exam exam : exams) {
                    out.println("[EXAM]");
                    out.println("ID: " + exam.getId());
                    out.println("Module: " + exam.getModuleName());
                    out.println("Title: " + exam.getTitle());
                    out.println("Description: " + exam.getDescription());
                    out.println("Duration: " + exam.getDuration());
                    out.println("[/EXAM]");
                    out.println();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    /**
     * Sets the relative exam file path
     */
    public static void setExamFilePath(String path) {
        examFilePath = path;
    }

    /**
     * Sets the absolute exam file path and ensures directory exists
     */
    public static void setRealExamFilePath(String path) {
        realExamFilePath = path;
        ensureDirectoryExists();
    }

    /**
     * Ensures the directory for the exam file exists
     */
    private static void ensureDirectoryExists() {
        if (realExamFilePath != null) {
            File examFile = new File(realExamFilePath);
            examFile.getParentFile().mkdirs();
            if (!examFile.exists()) {
                try {
                    examFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
