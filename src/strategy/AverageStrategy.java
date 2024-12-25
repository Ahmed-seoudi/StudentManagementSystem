package strategy;

import java.util.HashMap;
import java.util.Map;

public abstract class AverageStrategy implements EvaluationStrategy {
    private Map<String, Map<String, Double>> studentGrades = new HashMap<>(); // تخزين الدرجات

    @Override
    public void evaluate(String studentId, String courseName) {
        // يمكنك ترك هذه الدالة فارغة إذا لم تكن بحاجة إليها، أو استخدامها لتقييم الدرجات.
    }

    // إضافة درجات للطلاب
    public void addGrade(String studentId, String courseName, double grade) {
        studentGrades.putIfAbsent(studentId, new HashMap<>());
        studentGrades.get(studentId).put(courseName, grade);
    }

    // الحصول على المتوسط
    public double getAverage(String studentId, String courseName) {
        if (!studentGrades.containsKey(studentId)) {
            return -1; // الطالب غير موجود
        }

        Map<String, Double> grades = studentGrades.get(studentId);
        if (!grades.containsKey(courseName)) {
            return -1; // الكورس غير موجود للطالب
        }

        // حساب المتوسط فقط للدورات المسجلة
        double total = 0;
        int count = 0;
        for (Double grade : grades.values()) {
            total += grade;
            count++;
        }

        // إذا لم توجد درجات للطالب في الدورة المحددة
        if (count == 0) {
            return -1; // لا توجد درجات للطالب في الدورة
        }

        // حساب المتوسط
        return total / count;
    }
}
