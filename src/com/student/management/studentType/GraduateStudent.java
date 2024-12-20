package com.student.management.studentType;

import com.student.management.factory.Student;

public class GraduateStudent extends Student {
    
    // Constructor to initialize the GraduateStudent with ID and name
    public GraduateStudent(String id, String name) {
        super(id, name);
    }

    // Method to display details of the graduate student
    @Override
    public void displayDetails() {
        System.out.println("Graduate Student - ID: " + getId() + ", Name: " + getName());
    }
}
