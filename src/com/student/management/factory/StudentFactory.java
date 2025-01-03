package com.student.management.factory;

import com.student.management.studentType.GraduateStudent;
import com.student.management.studentType.PartTimeStudent;
import com.student.management.studentType.UndergraduateStudent;

public class StudentFactory {

    // Factory method لإنشاء طالب بناءً على النوع
    public static Student createStudent(String type, String id, String name) {
        switch (type.toLowerCase()) {
            case "undergraduate":
                return new UndergraduateStudent(id, name, type);  // تمرير النوع أيضًا
            case "graduate":
                return new GraduateStudent(id, name, type);  // تمرير النوع أيضًا
            case "part-time":
                return new PartTimeStudent(id, name, type);  // تمرير النوع أيضًا
            default:
                throw new IllegalArgumentException("Invalid student type: " + type);  // إرجاع استثناء في حالة النوع غير صحيح
        }
    }
}
