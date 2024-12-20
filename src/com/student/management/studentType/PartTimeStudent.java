package com.student.management.studentType;

import com.student.management.factory.Student;

public class PartTimeStudent extends Student {
    
    // Constructor to initialize the PartTimeStudent with ID and name
    public PartTimeStudent(String id, String name) {
        super(id, name);
    }

    // Method to display details of the part-time student
    @Override
    public void displayDetails() {
        System.out.println("Part-time Student - ID: " + getId() + ", Name: " + getName());
    }
}
