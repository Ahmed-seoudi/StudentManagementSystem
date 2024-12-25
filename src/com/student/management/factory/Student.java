package com.student.management.factory;

import state.StateContext; // استيراد StateContext
import state.UndergraduateState;
import state.GraduateState;
import state.PartTimeState;

public abstract class Student {
    public String id;
    public String name;
    public StateContext feeState;

    public Student(String id, String name, String type) {
        this.id = id;
        this.name = name;
        // بقية الكود كما هو
    }

    // Getter لـ id
   public String getId() {
    return id;
}


    // Getter لـ name
    public String getName() {
        return name;
    }

    // Getter لـ feeState
    public StateContext getFeeState() {
        return feeState;
    }

    // بقية الكود

    public void displayDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
