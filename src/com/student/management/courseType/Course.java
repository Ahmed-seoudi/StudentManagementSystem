package com.student.management.courseType;

public abstract class Course {
    public int courseId; // Course ID
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

    // Abstract method to determine if the course is core
    public abstract boolean isCore();

    // Abstract method to display course details
    abstract public void displayCourseDetails();

    // Method to get course description (throws exception if not implemented)
    public Object getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); // Method not implemented yet
    }

    // Method to get course cost (throws exception if not implemented)
    public Object[] getCost() {
        throw new UnsupportedOperationException("Not supported yet."); // Method not implemented yet
    }
}
