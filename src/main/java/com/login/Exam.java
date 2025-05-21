package com.exam.model;

import java.io.Serializable;

public class Exam implements Serializable {
    private String id;
    private String moduleName;
    private String title;
    private String description;
    private int duration; // in minutes
    
    public Exam() {
        // Empty constructor
    }
    
    public Exam(String id, String moduleName, String title, String description, int duration) {
        this.id = id;
        this.moduleName = moduleName;
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    @Override
    public String toString() {
        return id + "," + moduleName + "," + title + "," + description + "," + duration;
    }
}
