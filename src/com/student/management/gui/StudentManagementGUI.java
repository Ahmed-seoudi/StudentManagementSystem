package com.student.management.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import strategy.AverageStrategy;
import strategy.EvaluationStrategy;
import decorator.*;
import state.*;

// Student Class representing individual student details
class Student {
    String id, name, type;
    StateContext feeState;  // Added for state pattern to handle fee calculation based on student type

    public Student(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
        
        // Initialize fee state based on student type (using state design pattern)
        switch (type) {
            case "Undergraduate":
                this.feeState = new StateContext(new UndergraduateState());
                break;
            case "Graduate":
                this.feeState = new StateContext(new GraduateState());
                break;
            case "Part-Time":
                this.feeState = new StateContext(new PartTimeState());
                break;
            default:
                this.feeState = new StateContext(new UndergraduateState());
        }
    }
}

// StudentCourse Class representing the relationship between students and courses
class StudentCourse {
    String courseId, studentId, courseName, courseType, grade, courseTime;

    public StudentCourse(String courseId, String studentId, String courseName, String courseType, String grade, String courseTime) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.courseName = courseName;
        this.courseType = courseType;
        this.grade = grade;
        this.courseTime = courseTime;
    }
}

// Main StudentManagementGUI Class handling the graphical user interface
public class StudentManagementGUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JTextField studentNameField, studentIdField, courseIdField, courseNameField, gradeField;
    private JComboBox<String> studentTypeCombo, studentIdCombo, courseTypeCombo, courseDayCombo, courseTimeCombo, gradeStudentIdCombo, gradeCourseCombo, studentSelectorCombo;
    private JComboBox<String> evaluationStrategyCombo;
    private JTextArea registeredStudentsArea, registeredCoursesArea, gradesArea;
    private DefaultTableModel tableModel;
    private EvaluationStrategy evaluationStrategy;

    private List<Student> students = new ArrayList<>();
    private List<StudentCourse> courses = new ArrayList<>();

    // Constructor initializing the GUI
    public StudentManagementGUI() {
        frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        // Initialize the evaluation strategy (Average grade calculation)
        evaluationStrategy = new AverageStrategy() {
            @Override
            public double evaluate(double[] grades) {
                if (grades.length == 0) return 0;
                double sum = 0;
                for (double grade : grades) {
                    sum += grade;
                }
                return sum / grades.length;
            }

            @Override
            public double[] getGrades() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        tabbedPane = new JTabbedPane();
        studentIdCombo = new JComboBox<>();
        gradeStudentIdCombo = new JComboBox<>();
        studentSelectorCombo = new JComboBox<>();
        gradeCourseCombo = new JComboBox<>();
        evaluationStrategyCombo = new JComboBox<>(new String[]{"Average"});

        // Initialize tabs for student registration, course registration, grade assignment, and course display
        initStudentRegistrationTab();
        initCourseRegistrationTab();
        initGradeAssignmentTab();
        initStudentCourseDisplayTab();

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    // Student Registration Tab setup
    private void initStudentRegistrationTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        studentIdField = new JTextField();
        studentNameField = new JTextField();
        studentTypeCombo = new JComboBox<>(new String[]{"Undergraduate", "Graduate", "Part-Time"});
        JButton registerButton = new JButton("Register Student");

        // Input validation for student ID to ensure only digits are entered
        ((AbstractDocument) studentIdField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.insertString(fb, offset, text, attrs);
                }
            }
        });

        // Action listener for registering a student
        registerButton.addActionListener(e -> {
            String id = studentIdField.getText();
            String name = studentNameField.getText();
            String type = (String) studentTypeCombo.getSelectedItem();

            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (students.stream().anyMatch(student -> student.id.equals(id))) {
                JOptionPane.showMessageDialog(frame, "Student ID must be unique", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // استخدم Anonymous class لإنشاء الطالب
            Student student = new Student(id, name, type) {
                // يمكنك تخصيص الكود هنا إذا لزم الأمر
            };

            // إضافة الطالب إلى القائمة وتحديث واجهة المستخدم
            students.add(student);
            studentIdCombo.addItem(id);
            gradeStudentIdCombo.addItem(id);
            studentSelectorCombo.addItem(id);
            registeredStudentsArea.append(id + " - " + name + " - " + type + "\n");

            tableModel.addRow(new Object[]{name, id, "", "", "", "", ""});

            studentIdField.setText("");
            studentNameField.setText("");
        });

        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(studentIdField);
        inputPanel.add(new JLabel("Student Name:"));
        inputPanel.add(studentNameField);
        inputPanel.add(new JLabel("Student Type:"));
        inputPanel.add(studentTypeCombo);
        inputPanel.add(registerButton);

        registeredStudentsArea = new JTextArea();
        registeredStudentsArea.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(registeredStudentsArea), BorderLayout.CENTER);
        tabbedPane.addTab("Student Registration", panel);
    }

    // Course Registration Tab setup
    private void initCourseRegistrationTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        // Course registration fields
        courseIdField = new JTextField();
        courseNameField = new JTextField();
        courseTypeCombo = new JComboBox<>(new String[]{"Core", "Elective", "Lab"});
        courseDayCombo = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});
        courseTimeCombo = new JComboBox<>(new String[]{"9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM"});
        studentIdCombo = new JComboBox<>(); // Assuming populated elsewhere
        JButton registerCourseButton = new JButton("Register Course");

        // Action listener for course registration
        registerCourseButton.addActionListener(e -> {
            String courseId = courseIdField.getText();
            String courseName = courseNameField.getText();
            String courseType = (String) courseTypeCombo.getSelectedItem();
            String courseDay = (String) courseDayCombo.getSelectedItem();
            String courseTime = (String) courseTimeCombo.getSelectedItem();
            String studentId = (String) studentIdCombo.getSelectedItem();

            if (courseId.isEmpty() || courseName.isEmpty() || studentId == null) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Creating a base course and calculating discounted cost based on student type
            Course baseCourse = new BasicCourse(courseType);
            double baseCost = baseCourse.getCost();
            Student student = students.stream().filter(s -> s.id.equals(studentId)).findFirst().orElse(null);

            if (student == null) {
                JOptionPane.showMessageDialog(frame, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double discountedCost = student.feeState.calculateFee(baseCost);

            StudentCourse newCourse = new StudentCourse(courseId, studentId, courseName, courseType, "N/A", courseDay + " " + courseTime);
            courses.add(newCourse);
            gradeCourseCombo.addItem(courseName);
            
            registeredCoursesArea.append(String.format("%s - %s - %s - Original: $%.2f - Discounted: $%.2f\n", studentId, courseName, courseType, baseCost, discountedCost));
            
            String studentName = student.name;
            tableModel.addRow(new Object[]{
                studentName, studentId, courseName, courseId, courseDay + " " + courseTime, "N/A", String.format("$%.2f", discountedCost)
            });

            courseIdField.setText("");
            courseNameField.setText("");
        });

        inputPanel.add(new JLabel("Course ID:"));
        inputPanel.add(courseIdField);
        inputPanel.add(new JLabel("Course Name:"));
        inputPanel.add(courseNameField);
        inputPanel.add(new JLabel("Course Type:"));
        inputPanel.add(courseTypeCombo);
        inputPanel.add(new JLabel("Course Day:"));
        inputPanel.add(courseDayCombo);
        inputPanel.add(new JLabel("Course Time:"));
        inputPanel.add(courseTimeCombo);
        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(studentIdCombo);
        inputPanel.add(registerCourseButton);

        registeredCoursesArea = new JTextArea();
        registeredCoursesArea.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(registeredCoursesArea), BorderLayout.CENTER);
        tabbedPane.addTab("Course Registration", panel);
    }

    // Grade Assignment Tab setup
    private void initGradeAssignmentTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        gradeField = new JTextField();
        JButton assignGradeButton = new JButton("Assign Grade");
        JButton showAverageButton = new JButton("Show Average");

        // Action listener for assigning a grade to a student
        assignGradeButton.addActionListener(e -> {
            String studentId = (String) gradeStudentIdCombo.getSelectedItem();
            String courseName = (String) gradeCourseCombo.getSelectedItem();
            String grade = gradeField.getText();

            if (studentId == null || courseName == null || grade.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double numericGrade = Double.parseDouble(grade);
                evaluationStrategy.addGrade(studentId, courseName, numericGrade);

                boolean gradeAssigned = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String id = (String) tableModel.getValueAt(i, 1);
                    String courseInTable = (String) tableModel.getValueAt(i, 2);
                    if (id.equals(studentId) && courseInTable.equals(courseName)) {
                        tableModel.setValueAt(grade, i, 5);
                        gradeAssigned = true;
                        break;
                    }
                }

                if (!gradeAssigned) {
                    JOptionPane.showMessageDialog(frame, "Student or course not found", "Error", JOptionPane.ERROR_MESSAGE);
                }

                gradesArea.append(studentId + " - " + courseName + " - " + grade + "\n");
                gradeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid numeric grade", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action listener for showing average grade
        showAverageButton.addActionListener(e -> {
            double[] grades = evaluationStrategy.getGrades();
            double average = evaluationStrategy.evaluate(grades);
            JOptionPane.showMessageDialog(frame, "Average grade: " + average, "Average", JOptionPane.INFORMATION_MESSAGE);
        });

        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(gradeStudentIdCombo);
        inputPanel.add(new JLabel("Course Name:"));
        inputPanel.add(gradeCourseCombo);
        inputPanel.add(assignGradeButton);
        inputPanel.add(showAverageButton);

        gradesArea = new JTextArea();
        gradesArea.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(gradesArea), BorderLayout.CENTER);
        tabbedPane.addTab("Grade Assignment", panel);
    }

    // Student Course Display Tab setup
    private void initStudentCourseDisplayTab() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup for displaying student and course details
        tableModel = new DefaultTableModel(new String[]{
                "Student Name", "Student ID", "Course Name", "Course ID", "Course Time", "Grade", "Cost"
        }, 0);

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Student Courses", panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
    }
}



