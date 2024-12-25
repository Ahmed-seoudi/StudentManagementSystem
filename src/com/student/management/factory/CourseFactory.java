
package com.student.management.factory;

import com.student.management.courseType.*;

public class CourseFactory {

    /**
     * Factory method to create a course based on type.
     *
     * @param courseType The type of the course ("core", "elective", "lab").
     * @param courseId The unique ID for the course.
     * @param courseName The name of the course.
     * @return The appropriate Course instance.
     */
    public static Course createCourse(String courseType, int courseId, String courseName) {
        switch (courseType.toLowerCase()) {
            case "core":
                return new CoreCourse(courseId, courseName); // Create a core course
            case "elective":
                return new ElectiveCourse(courseId, courseName); // Create an elective course
            case "lab":
                return new LabCourse(courseId, courseName); // Create a lab course
            default:
                throw new IllegalArgumentException("Invalid course type: " + courseType);
        }
    }
}
