
package com.student.management.courseType;

public class LabCourse extends Course {
    private static final double LAB_COST = 200.0; // Additional fixed cost for lab courses

    // Constructor to initialize course details
    public LabCourse(int courseId, String courseName) {
        super(courseId, courseName);
    }


    @Override
    public void displayCourseDetails() {
        // Display course information specific to lab courses
        System.out.println("Course ID: " + getCourseId() + ", Course Name: " + getCourseName() + ", Type: Lab Course, Cost: $" + LAB_COST);
    }

    @Override
    public double getCost() {
        return LAB_COST; // Return the cost for lab courses
    }

    @Override
    public String getDescription() {
        return "Lab course: " + getCourseName(); // Provide course description
    }
}
