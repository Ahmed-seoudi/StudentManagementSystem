package com.student.management.singleton;

import com.student.management.courseType.CoreCourse;
import com.student.management.courseType.Course;
import com.student.management.factory.CourseFactory;
import java.util.*;

public class CourseRegistrationSystem {
    private static CourseRegistrationSystem instance; 
    private final Map<String, Set<String>> studentCourses; 

    private CourseRegistrationSystem() {
        studentCourses = new HashMap<>();
    }

    public static CourseRegistrationSystem getInstance() {
        if (instance == null) {
            instance = new CourseRegistrationSystem();
        }
        return instance;
    }

    public boolean registerCourse(String studentId, com.student.management.courseType.Course course) {
        studentCourses.putIfAbsent(studentId, new HashSet<>());
        Set<String> courses = studentCourses.get(studentId);

        if (courses.contains(course.getCourseName())) {
            System.out.println("Student already registered for this course.");
            return false;
        }

        courses.add(course.getCourseName());
        System.out.println("Course registered successfully for student ID: " + studentId);

        GradeProcessingSystem.getInstance().addGrade(studentId, course.getCourseName(), 0.0);
        return true;
    }

    public void displayStudentCourses(String s101) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


