
package com.student.management.courseType;

public abstract class Course {
    private int courseId; // Course ID
    private String courseName; // Course Name

    // Constructor to initialize course ID and name
    public Course(int courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    // Getter for course ID
    public int getCourseId() {
        return courseId;
    }

    // Getter for course name
    public String getCourseName() {
        return courseName;
    }


    // Abstract method to display course details
    public abstract void displayCourseDetails();

    // Abstract method to get course cost
    public abstract double getCost();

    // Abstract method to get course description
    public abstract String getDescription();
}
