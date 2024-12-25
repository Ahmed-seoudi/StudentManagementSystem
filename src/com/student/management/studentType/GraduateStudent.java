package com.student.management.studentType;

import com.student.management.factory.Student;

public class GraduateStudent extends Student {

    public GraduateStudent(String id, String name) {
        super(id, name);
    }

    @Override
    public void displayDetails() {
        System.out.println("Graduate Student - ID: " + getId() + ", Name: " + getName());
    }
}
