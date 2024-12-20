package com.student.management;

import com.student.management.courseType.CoreCourse;
import com.student.management.courseType.Course;
import com.student.management.factory.*;
import com.student.management.singleton.*;

public class MainApp {
    public static void main(String[] args) {
        try {
            // Student Registration using Factory Pattern
            System.out.println("--- Student Registration ---");
            // Creating students of different types (undergraduate, graduate, part-time)
            Student student1 = StudentFactory.createStudent("undergraduate", "S101", "Alice");
            Student student2 = StudentFactory.createStudent("graduate", "S102", "Bob");
            Student student3 = StudentFactory.createStudent("part-time", "S103", "Charlie");

            // Display student details
            student1.displayDetails();
            student2.displayDetails();
            student3.displayDetails();

            // Course Registration
            CourseRegistrationSystem courseRegSys = CourseRegistrationSystem.getInstance();
            System.out.println("\n--- Course Registration ---");
            // Registering courses for students
            registerCourses(courseRegSys);

            // Display Student Courses
            System.out.println("\n--- Course Registration System ---");
            courseRegSys.displayStudentCourses("S101");
            courseRegSys.displayStudentCourses("S102");

            // Grade Processing
            GradeProcessingSystem gradeSystem = GradeProcessingSystem.getInstance();
            System.out.println("\n--- Grade Processing System ---");
            // Processing grades for students
            processGrades(gradeSystem);

            // Display Grades for students
            gradeSystem.displayGrades("S101");
            gradeSystem.displayGrades("S102");

            // Course Creation using Factory Pattern
            System.out.println("\n--- Factory Pattern: Course Creation ---");
            // Creating courses (core and elective)
            Course coreCourse = CourseFactory.createCourse(1, "Math 101", true);
            Course electiveCourse = CourseFactory.createCourse(2, "History 201", false);

            // Display course details
            coreCourse.displayCourseDetails();
            electiveCourse.displayCourseDetails();

        } catch (Exception e) {
            // Handle any exceptions that occur during the process
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to register courses for students
    private static void registerCourses(CourseRegistrationSystem courseRegSys) {
        courseRegSys.registerCourse("S101", new CoreCourse(1, "CSE101"));
        courseRegSys.registerCourse("S101", new CoreCourse(2, "CSE102"));
        courseRegSys.registerCourse("S102", new CoreCourse(1, "CSE101"));
    }

    // Method to process grades for students
    private static void processGrades(GradeProcessingSystem gradeSystem) {
        gradeSystem.addGrade("S101", "CSE101", 85.5);
        gradeSystem.addGrade("S101", "CSE102", 90.0);
        gradeSystem.addGrade("S102", "CSE101", 78.0);
    }
}
