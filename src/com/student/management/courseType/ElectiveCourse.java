package com.student.management.courseType;

public class ElectiveCourse extends Course {
    public static final double COURSE_COST = 350.0; // Cost of the elective course

    public ElectiveCourse(int courseId, String courseName) {
        super(courseId, courseName); // Calling the superclass constructor
    }

    @Override
    public boolean isCore() {
        return false; // Indicating that this is not a core course, but an elective course
    }

    @Override
    public void displayCourseDetails() {
        // Displaying course details including ID, name, type, and cost
        System.out.println("Course ID: " + getCourseId() + ", Course Name: " + getCourseName() + ", Type: Elective Course, Cost: $" + COURSE_COST);
    }

    @Override
    public Object getDescription() {
        // Returning the description for the elective course
        return "Elective course: " + getCourseName();
    }

    @Override
    public Object[] getCost() {
        // Returning the cost for the elective course
        return new Object[]{COURSE_COST};
    }
}
