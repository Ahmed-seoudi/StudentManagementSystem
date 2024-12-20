package com.student.management.courseType;

public class CoreCourse extends Course {
    private static final double COURSE_COST = 500.0; // Fixed cost for the core course

    public CoreCourse(int courseId, String courseName) {
        super(courseId, courseName); // Calling the superclass constructor
    }

    @Override
    public boolean isCore() {
        return true; // Indicating that this is a core course
    }

    @Override
    public void displayCourseDetails() {
        // Display course details including ID, name, type, and cost
        System.out.println("Course ID: " + getCourseId() + ", Course Name: " + getCourseName() + ", Type: Core Course, Cost: $" + COURSE_COST);
    }

    @Override
    public Object getDescription() {
        // Returning the description for the core course
        return "Core course: " + getCourseName();
    }

    @Override
    public Object[] getCost() {
        // Returning the cost for the core course
        return new Object[]{COURSE_COST};
    }
}
