
package com.student.management.courseType;

public class ElectiveCourse extends Course {
    private static final double COURSE_COST = 350.0; // Fixed cost for elective courses

    // Constructor to initialize course details
    public ElectiveCourse(int courseId, String courseName) {
        super(courseId, courseName);
    }


    @Override
    public void displayCourseDetails() {
        // Display course information specific to elective courses
        System.out.println("Course ID: " + getCourseId() + ", Course Name: " + getCourseName() + ", Type: Elective Course, Cost: $" + COURSE_COST);
    }

    @Override
    public double getCost() {
        return COURSE_COST; // Return the cost for elective courses
    }

    @Override
    public String getDescription() {
        return "Elective course: " + getCourseName(); // Provide course description
    }
}
