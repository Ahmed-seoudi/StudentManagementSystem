package com.student.management.studentType;

import com.student.management.factory.Student;

public class UndergraduateStudent extends Student {
    
    // Constructor to initialize the UndergraduateStudent with ID and name
    public UndergraduateStudent(String id, String name) {
        super(id, name);
    }

    // Method to display details of the undergraduate student
    @Override
    public void displayDetails() {
        System.out.println("Undergraduate Student - ID: " + getId() + ", Name: " + getName());
    }
}
