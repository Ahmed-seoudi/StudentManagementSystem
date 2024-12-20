package com.student.management.singleton;

import com.student.management.courseType.CoreCourse;

import java.util.*;

public class CourseRegistrationSystem {
    private static CourseRegistrationSystem instance; // Singleton instance
    private final Map<String, Set<String>> studentCourses; // Map: Student ID -> Set of Course Names

    // Private constructor to prevent instantiation from outside
    private CourseRegistrationSystem() {
        studentCourses = new HashMap<>();
    }

    // Method to get the singleton instance of the CourseRegistrationSystem
    public static CourseRegistrationSystem getInstance() {
        if (instance == null) {
            instance = new CourseRegistrationSystem();
        }
        return instance;
    }

    // Method to register a course for a student
    public boolean registerCourse(String studentId, CoreCourse course) {
        studentCourses.putIfAbsent(studentId, new HashSet<>());
        Set<String> courses = studentCourses.get(studentId);

        // Check if the student is already registered for the course
        if (courses.contains(course.getCourseName())) {
            System.out.println("Student already registered for this course.");
            return false;
        }

        // Register the student for the course
        courses.add(course.getCourseName());
        System.out.println("Course registered successfully for student ID: " + studentId);
        return true;
    }

    // Method to display the courses a student is registered for
    public void displayStudentCourses(String studentId) {
        Set<String> courses = studentCourses.get(studentId);
        
        // If no courses are registered, display a message
        if (courses == null || courses.isEmpty()) {
            System.out.println("No courses registered for student ID: " + studentId);
        } else {
            System.out.println("Courses for student ID " + studentId + ": " + courses);
        }
    }
}
