package com.student.management.studentType;

import com.student.management.factory.Student;

public class PartTimeStudent extends Student {

    public PartTimeStudent(String id, String name) {
        super(id, name);
    }

    @Override
    public void displayDetails() {
        System.out.println("Part-time Student - ID: " + getId() + ", Name: " + getName());
    }
}
