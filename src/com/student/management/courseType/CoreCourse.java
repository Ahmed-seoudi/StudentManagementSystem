
package com.student.management.courseType;

public class CoreCourse extends Course {
    private static final double COURSE_COST = 500.0; // Fixed cost for core courses

    // Constructor to initialize course details
    public CoreCourse(int courseId, String courseName) {
        super(courseId, courseName);
    }

    
    @Override
    public void displayCourseDetails() {
        // Display course information specific to core courses
        System.out.println("Course ID: " + getCourseId() + ", Course Name: " + getCourseName() + ", Type: Core Course, Cost: $" + COURSE_COST);
    }

    @Override
    public double getCost() {
        return COURSE_COST; // Return the cost for core courses
    }

    @Override
    public String getDescription() {
        return "Core course: " + getCourseName(); // Provide course description
    }
}
