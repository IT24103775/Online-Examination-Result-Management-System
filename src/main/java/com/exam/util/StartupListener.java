package com.exam.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener that runs before AppContextListener to set the webapp path.
 * This ensures that the webapp path is set before any file operations occur.
 */
@WebListener
public class StartupListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Try to find the webapp path from the system properties first
        String webappPath = System.getProperty("webapp.path");
        
        if (webappPath == null) {
            // If not set, try to determine it from the classpath
            String webInfPath = sce.getServletContext().getRealPath("/WEB-INF");
            if (webInfPath != null) {
                String projectRoot = webInfPath.replace("target" + System.getProperty("file.separator") + 
                                                      "online-exam-system" + System.getProperty("file.separator") + 
                                                      "WEB-INF", "");
                
                webappPath = projectRoot + "src" + System.getProperty("file.separator") + 
                            "main" + System.getProperty("file.separator") + "webapp";
                
                System.setProperty("webapp.path", webappPath);
                System.out.println("StartupListener: Setting webapp path to " + webappPath);
            }
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nothing to do here
    }
}
