package com.student.management.studentType;

import com.student.management.factory.Student;

public class UndergraduateStudent extends Student {

    public UndergraduateStudent(String id, String name, String type) {
        super(id, name, type); // تمرير النوع إلى الـ Constructor في الصف الأساسي
    }


    public void displayDetails() {
        System.out.println("Undergraduate Student - ID: " + getId() + ", Name: " + getName());
    }
}
