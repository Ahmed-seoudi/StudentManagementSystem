package com.student.management.studentType;

import com.student.management.factory.Student;

public class PartTimeStudent extends Student {

    public PartTimeStudent(String id, String name, String type) {
        super(id, name, type); // تمرير النوع إلى الـ Constructor في الصف الأساسي
    }


    public void displayDetails() {
        System.out.println("Part-time Student - ID: " + getId() + ", Name: " + getName());
    }
}
