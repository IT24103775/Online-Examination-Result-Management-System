package com.exam.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.nio.file.Paths;

@WebListener
public class AppContextListener implements ServletContextListener {
    
    // Path constant for source code webapp directory
    private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        
        // Try to get the webapp path from system properties
        String webappPath = System.getProperty("webapp.path");
        
        if (webappPath == null) {
            // Use context real path to get a reference point
            String contextPath = context.getRealPath("/");
            
            // Look for webapp directory by searching up from the target directory
            File currentDir = new File(contextPath).getParentFile();
            while (currentDir != null && !Paths.get(currentDir.getPath(), DEFAULT_WEBAPP_PATH).toFile().exists()) {
                currentDir = currentDir.getParentFile();
            }
            
            if (currentDir != null) {
                // Found the project root, set webapp path
                webappPath = Paths.get(currentDir.getPath(), DEFAULT_WEBAPP_PATH).toString();
                System.setProperty("webapp.path", webappPath);
            } else {
                // Fall back to context real path if webapp directory can't be found
                webappPath = contextPath;
            }
        }
        
        // Set data paths using the webapp path
        String userDataPath = Paths.get(webappPath, "WEB-INF", "data", "users.txt").toString();
        String examDataPath = Paths.get(webappPath, "WEB-INF", "data", "exam.txt").toString();
        String questionDataPath = Paths.get(webappPath, "WEB-INF", "data", "questions.txt").toString();
        
        // Set the paths in the handler classes
        FileHandler.setRealUserFilePath(userDataPath);
        ExamFileHandler.setRealExamFilePath(examDataPath);
        
        // Create the data directory if it doesn't exist
        File dataDir = new File(Paths.get(webappPath, "WEB-INF", "data").toString());
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        System.out.println("Application initialized with data paths:");
        System.out.println("Webapp path: " + webappPath);
        System.out.println("User data: " + userDataPath);
        System.out.println("Exam data: " + examDataPath);
        System.out.println("Question data: " + questionDataPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup if needed
    }
}
