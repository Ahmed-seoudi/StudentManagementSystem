package com.student.management.singleton;

import java.util.*;

public class GradeProcessingSystem {
    private static GradeProcessingSystem instance; // Singleton instance

    private final Map<String, Map<String, Double>> studentGrades; // Map: Student ID -> (Course ID -> Grade)

    // Private constructor to prevent instantiation from outside
    private GradeProcessingSystem() {
        studentGrades = new HashMap<>();
    }

    // Method to get the singleton instance of the GradeProcessingSystem
    public static GradeProcessingSystem getInstance() {
        if (instance == null) {
            instance = new GradeProcessingSystem();
        }
        return instance;
    }

    // Method to add a grade for a student in a specific course
    public void addGrade(String studentId, String courseId, double grade) {
        studentGrades.putIfAbsent(studentId, new HashMap<>());
        studentGrades.get(studentId).put(courseId, grade);
        System.out.println("Grade added for student " + studentId + " in course " + courseId);
    }

    // Method to display the grades for a student
    public void displayGrades(String studentId) {
        Map<String, Double> grades = studentGrades.get(studentId);

        // If no grades are available, display a message
        if (grades == null || grades.isEmpty()) {
            System.out.println("No grades available for student " + studentId);
        } else {
            System.out.println("Grades for student " + studentId + ":");
            // Iterate through the courses and display the grade for each
            grades.forEach((courseId, grade) -> System.out.println("Course: " + courseId + ", Grade: " + grade));
        }
    }
}
