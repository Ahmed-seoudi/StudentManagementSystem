package com.student.management.factory;

import com.student.management.studentType.GraduateStudent;
import com.student.management.studentType.PartTimeStudent;
import com.student.management.studentType.UndergraduateStudent;

public class StudentFactory {
    
    // Factory method to create a student based on their type (undergraduate, graduate, or part-time)
    public static Student createStudent(String type, String id, String name) {
        switch (type.toLowerCase()) {
            case "undergraduate":
                // Create and return an undergraduate student
                return new UndergraduateStudent(id, name);
            case "graduate":
                // Create and return a graduate student
                return new GraduateStudent(id, name);
            case "part-time":
                // Create and return a part-time student
                return new PartTimeStudent(id, name);
            default:
                // Throw an exception if the student type is invalid
                throw new IllegalArgumentException("Invalid student type: " + type);
        }
    }
}
