package com.exam.util;

import com.exam.model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static String userFilePath = "/WEB-INF/data/users.txt";
    private static String realUserFilePath;
    
    /**
     * Saves a new user to the file
     */
    public static boolean saveUser(User user) {
        ensureDirectoryExists();
        try (FileWriter fw = new FileWriter(realUserFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println("[USER]");
            out.println("Username: " + user.getUsername());
            out.println("Password: " + user.getPassword());
            out.println("Email: " + user.getEmail());
            out.println("Role: " + user.getRole());
            out.println("[/USER]");
            out.println();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Gets all users from the file
     */
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        
        File file = new File(realUserFilePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return users;
            } catch (IOException e) {
                e.printStackTrace();
                return users;
            }
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(realUserFilePath))) {
            String line;
            User currentUser = null;
            
            while ((line = br.readLine()) != null) {
                line = line.trim();
                
                if (line.equals("[USER]")) {
                    currentUser = new User();
                } else if (line.startsWith("Username: ")) {
                    if (currentUser != null) {
                        currentUser.setUsername(line.substring("Username: ".length()));
                    }
                } else if (line.startsWith("Password: ")) {
                    if (currentUser != null) {
                        currentUser.setPassword(line.substring("Password: ".length()));
                    }
                } else if (line.startsWith("Email: ")) {
                    if (currentUser != null) {
                        currentUser.setEmail(line.substring("Email: ".length()));
                    }
                } else if (line.startsWith("Role: ")) {
                    if (currentUser != null) {
                        currentUser.setRole(line.substring("Role: ".length()));
                    }
                } else if (line.equals("[/USER]")) {
                    if (currentUser != null) {
                        users.add(currentUser);
                        currentUser = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return users;
    }
    
    /**
     * Validates user credentials against stored data
     */
    public static User validateUser(String username, String password) {
        List<User> users = getAllUsers();
        
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        
        return null;
    }
    
    /**
     * Updates a user's information with a new User object
     */
    public static boolean updateUser(String oldUsername, User updatedUser) {
        try {
            List<User> users = getAllUsers();
            boolean found = false;
            
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equals(oldUsername)) {
                    users.set(i, updatedUser);
                    found = true;
                    break;
                }
            }
            
            if (found) {
                // Replace saveAllUsers with rewriteUserFile to properly update the text file
                return rewriteUserFile(users);
            }
            
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes a user by username
     */
    public static boolean deleteUser(String username) {
        List<User> allUsers = getAllUsers();
        int initialSize = allUsers.size();
        
        allUsers.removeIf(user -> user.getUsername().equals(username));
        
        if (allUsers.size() == initialSize) {
            return false;
        }
        
        return writeUsersToFile(allUsers);
    }
    
    /**
     * Finds a user by their username
     */
    public static User getUserByUsername(String username) {
        List<User> allUsers = getAllUsers();
        
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        
        return null;
    }
    
    /**
     * Updates specific fields of a user
     */
    public static boolean updateUser(String username, String email, String password) {
        List<User> allUsers = getAllUsers();
        User updatedUser = null;
        
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                user.setEmail(email);
                
                if (password != null && !password.trim().isEmpty()) {
                    user.setPassword(password);
                }
                
                updatedUser = user;
                break;
            }
        }
        
        if (updatedUser == null) {
            return false;
        }
        
        return writeUsersToFile(allUsers);
    }
    
    /**
     * Saves all users to a binary file
     */
    private static boolean saveAllUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Writes the list of users to the text file
     */
    private static boolean writeUsersToFile(List<User> users) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(realUserFilePath));
            
            for (User user : users) {
                writer.write("[USER]");
                writer.newLine();
                writer.write("Username: " + user.getUsername());
                writer.newLine();
                writer.write("Password: " + user.getPassword());
                writer.newLine();
                writer.write("Email: " + user.getEmail());
                writer.newLine();
                writer.write("Role: " + user.getRole());
                writer.newLine();
                writer.write("[/USER]");
                writer.newLine();
                writer.newLine();
            }
            
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Rewrites the entire user file with updated content
     */
    private static boolean rewriteUserFile(List<User> users) {
        try (FileWriter fw = new FileWriter(realUserFilePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            for (User user : users) {
                out.println("[USER]");
                out.println("Username: " + user.getUsername());
                out.println("Password: " + user.getPassword());
                out.println("Email: " + user.getEmail());
                out.println("Role: " + user.getRole());
                out.println("[/USER]");
                out.println(); 
            }
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Sets the relative user file path
     */
    public static void setUserFilePath(String path) {
        userFilePath = path;
    }
    
    /**
     * Sets the absolute user file path and ensures directory exists
     */
    public static void setRealUserFilePath(String path) {
        realUserFilePath = path;
        ensureDirectoryExists();
    }
    
    /**
     * Ensures the directory for the user file exists
     */
    private static void ensureDirectoryExists() {
        if (realUserFilePath != null) {
            File file = new File(realUserFilePath);
            File directory = file.getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
