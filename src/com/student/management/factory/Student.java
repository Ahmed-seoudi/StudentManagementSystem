package com.student.management.factory;

public abstract class Student {
    public String id;  // معرف الطالب
    public String name;  // اسم الطالب

    // Constructor لتعيين المعرف والاسم
    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter للحصول على المعرف
    public String getId() {
        return id;
    }

    // Getter للحصول على الاسم
    public String getName() {
        return name;
    }

    // دالة abstract لعرض تفاصيل الطالب
    public abstract void displayDetails();

    public String getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  }
