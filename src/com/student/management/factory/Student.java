package com.student.management.factory;

public abstract class Student {
    private String id; // Student ID
    private String name; // Student name

    // Constructor to initialize student ID and name
    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for student ID
    public String getId() {
        return id;
    }

    // Getter for student name
    public String getName() {
        return name;
    }

    // Abstract method to display student details
    public abstract void displayDetails();
}
