package com.student.management.studentType;

import com.student.management.factory.Student;

public class UndergraduateStudent extends Student {

    public UndergraduateStudent(String id, String name) {
        super(id, name);
    }

    @Override
    public void displayDetails() {
        System.out.println("Undergraduate Student - ID: " + getId() + ", Name: " + getName());
    }
}
