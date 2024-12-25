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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    Object getId() {
        return id;
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

    private List<com.student.management.factory.Student> students = new ArrayList<>();
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
                throw new UnsupportedOperationException("Not supported yet.");
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

            if (students.stream().anyMatch(student -> student.getId().equals(id))) {
                JOptionPane.showMessageDialog(frame, "Student ID must be unique", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Create the student using the StudentFactory
                com.student.management.factory.Student student = 
                    com.student.management.factory.StudentFactory.createStudent(type, id, name);

                // Add the student to the list and update UI
                students.add(student);
                studentIdCombo.addItem(id);
                gradeStudentIdCombo.addItem(id);
                studentSelectorCombo.addItem(id);
                registeredStudentsArea.append(id + " - " + name + " - " + type + "\n");

                tableModel.addRow(new Object[]{name, id, "", "", "", "", ""});

                studentIdField.setText("");
                studentNameField.setText("");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
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
    JPanel inputPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    courseIdField = new JTextField();
    courseNameField = new JTextField();
    courseTypeCombo = new JComboBox<>(new String[]{"Core", "Elective", "Lab"});
    courseDayCombo = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});
    courseTimeCombo = new JComboBox<>(new String[]{"9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM"});
    JButton registerCourseButton = new JButton("Register Course");

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

        // Use CourseFactory to create course based on selected type
        com.student.management.courseType.Course course = com.student.management.factory.CourseFactory.createCourse(courseType, Integer.parseInt(courseId), courseName);

        // Register course for the student
        courses.add(new StudentCourse(courseId, studentId, courseName, courseType, "N/A", courseDay + " " + courseTime));

        gradeCourseCombo.addItem(courseName);
        registeredCoursesArea.append(studentId + " - " + courseName + " - " + courseType + "\n");
        courseIdField.setText("");
        courseNameField.setText("");
    });

    gbc.gridx = 0;
    gbc.gridy = 0;
    inputPanel.add(new JLabel("Course ID:"), gbc);
    gbc.gridx = 1;
    inputPanel.add(courseIdField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    inputPanel.add(new JLabel("Course Name:"), gbc);
    gbc.gridx = 1;
    inputPanel.add(courseNameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    inputPanel.add(new JLabel("Course Type:"), gbc);
    gbc.gridx = 1;
    inputPanel.add(courseTypeCombo, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    inputPanel.add(new JLabel("Course Day:"), gbc);
    gbc.gridx = 1;
    inputPanel.add(courseDayCombo, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    inputPanel.add(new JLabel("Course Time:"), gbc);
    gbc.gridx = 1;
    inputPanel.add(courseTimeCombo, gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    inputPanel.add(new JLabel("Student ID:"), gbc);
    gbc.gridx = 1;
    inputPanel.add(studentIdCombo, gbc);

    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 2;
    inputPanel.add(registerCourseButton, gbc);

    registeredCoursesArea = new JTextArea();
    registeredCoursesArea.setEditable(false);

    panel.add(inputPanel, BorderLayout.NORTH);
    panel.add(new JScrollPane(registeredCoursesArea), BorderLayout.CENTER);
    tabbedPane.addTab("Course Registration", panel);
}


    private void initGradeAssignmentTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        gradeField = new JTextField();
        JButton assignGradeButton = new JButton("Assign Grade");

        assignGradeButton.addActionListener(e -> {
            String studentId = (String) gradeStudentIdCombo.getSelectedItem();
            String courseName = (String) gradeCourseCombo.getSelectedItem();
            String grade = gradeField.getText();

            if (studentId == null || courseName == null || grade.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            courses.stream()
                    .filter(course -> course.studentId.equals(studentId) && course.courseName.equals(courseName))
                    .forEach(course -> course.grade = grade);

            gradesArea.append(studentId + " - " + courseName + " - " + grade + "\n");
            gradeField.setText("");
        });

        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(gradeStudentIdCombo);
        inputPanel.add(new JLabel("Course Name:"));
        inputPanel.add(gradeCourseCombo);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(assignGradeButton);

        gradesArea = new JTextArea();
        gradesArea.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(gradesArea), BorderLayout.CENTER);
        tabbedPane.addTab("Assign Grades", panel);
    }

    private void initStudentCourseDisplayTab() {
        JPanel panel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new String[]{"Student ID", "Course Name", "Course Type", "Course Time", "Grade"}, 0);
        JTable table = new JTable(tableModel);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            tableModel.setRowCount(0);
            for (StudentCourse course : courses) {
                tableModel.addRow(new Object[]{course.studentId, course.courseName, course.courseType, course.courseTime, course.grade});
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Time Table", panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
    }
}

