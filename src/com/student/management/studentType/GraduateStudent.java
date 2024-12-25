package com.student.management.studentType;

import com.student.management.factory.Student;

public class GraduateStudent extends Student {

    public GraduateStudent(String id, String name, String type) {
        super(id, name, type); // تمرير النوع إلى الـ Constructor في الصف الأساسي
    }



    public void displayDetails() {
        System.out.println("Graduate Student - ID: " + getId() + ", Name: " + getName());
    }
}
