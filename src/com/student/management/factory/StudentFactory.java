package com.student.management.factory;

import com.student.management.studentType.GraduateStudent;
import com.student.management.studentType.PartTimeStudent;
import com.student.management.studentType.UndergraduateStudent;

public class StudentFactory {

    // Factory method لإنشاء طالب بناءً على النوع
    public static Student createStudent(String type, String id, String name) {
        switch (type.toLowerCase()) {
            case "undergraduate":
                return new UndergraduateStudent(id, name);  // إنشاء طالب من النوع Undergraduate
            case "graduate":
                return new GraduateStudent(id, name);  // إنشاء طالب من النوع Graduate
            case "part-time":
                return new PartTimeStudent(id, name);  // إنشاء طالب من النوع Part-Time
            default:
                throw new IllegalArgumentException("Invalid student type: " + type);  // إرجاع استثناء في حالة النوع غير صحيح
        }
    }
}
