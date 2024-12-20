package com.student.management.courseType;

import decorator.Course;
import decorator.CourseDecorator;

public class LabCourse extends CourseDecorator {
    private static final double LAB_COST = 200.0; // Additional cost for the lab (can be modified)

    public LabCourse(Course decoratedCourse) {
        super(decoratedCourse); // Passing the decorated (base) course
    }

    @Override
    public String getDescription() {
        // Adding lab description to the base course description
        return super.getDescription() + " + Lab";
    }

    @Override
    public double getCost() {
        // Adding the lab cost to the base course cost
        return super.getCost() + LAB_COST;
    }
}
