package com.exam.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for setting the webapp path before the application starts.
 * Can be used in development to ensure data is saved in the source webapp directory.
 */
public class WebappPathSetter {
    
    /**
     * Sets the webapp.path system property to the specified path.
     * @param webappPath the path to the webapp directory
     */
    public static void setWebappPath(String webappPath) {
        Path path = Paths.get(webappPath);
        if (Files.exists(path) && Files.isDirectory(path)) {
            System.setProperty("webapp.path", webappPath);
            System.out.println("Webapp path set to: " + webappPath);
        } else {
            System.err.println("Invalid webapp path: " + webappPath);
        }
    }
    
    /**
     * Attempts to locate the webapp directory automatically.
     * Starts from the current working directory and searches up the directory tree.
     */
    public static void locateAndSetWebappPath() {
        String userDir = System.getProperty("user.dir");
        Path currentDir = Paths.get(userDir);
        
        // Check if the current directory contains src/main/webapp
        Path webappPath = findWebappInParentDirectories(currentDir);
        
        if (webappPath != null) {
            setWebappPath(webappPath.toString());
        } else {
            System.err.println("Could not locate webapp directory starting from: " + userDir);
        }
    }
    
    private static Path findWebappInParentDirectories(Path startDir) {
        Path current = startDir;
        int maxDepth = 5; // Limit how far up we go
        
        for (int i = 0; i < maxDepth && current != null; i++) {
            Path webappDir = current.resolve("src/main/webapp");
            if (Files.exists(webappDir) && Files.isDirectory(webappDir)) {
                return webappDir;
            }
            current = current.getParent();
        }
        
        return null;
    }
}
