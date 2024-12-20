package com.student.management.factory;

import com.student.management.courseType.CoreCourse;
import com.student.management.courseType.Course;
import com.student.management.courseType.ElectiveCourse;

public class CourseFactory {

    // Factory method to create a course based on whether it's core or elective
    public static Course createCourse(int courseId, String courseName, boolean isCore) {
        if (isCore) {
            // Create and return a core course if the flag is true
            return new CoreCourse(courseId, courseName);
        } else {
            // Create and return an elective course if the flag is false
            return new ElectiveCourse(courseId, courseName);
        }
    }

    // Factory method to create a course based on a string indicating course type
    public static Course createCourse(String courseType, int courseId, String courseName) {
        switch (courseType.toLowerCase()) {
            case "core":
                // Return a core course if the type is "core"
                return new CoreCourse(courseId, courseName);
            case "elective":
                // Return an elective course if the type is "elective"
                return new ElectiveCourse(courseId, courseName);
            default:
                // Throw an exception if an invalid course type is provided
                throw new IllegalArgumentException("Invalid course type: " + courseType);
        }
    }
}
