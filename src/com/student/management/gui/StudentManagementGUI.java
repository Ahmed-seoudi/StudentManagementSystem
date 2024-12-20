package com.student.management.gui;

import strategy.AverageStrategy;
import strategy.EvaluationStrategy;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;


// Class to represent a Student with ID, Name, and Type
class Student {
    String id, name, type;
    public Student(String id, String name, String type) {
        this.id = id; this.name = name; this.type = type;
    }
}

// Class to represent a Student's Course details
class StudentCourse {
    String courseId, studentId, courseName, courseType, grade, courseTime;
    public StudentCourse(String courseId, String studentId, String courseName, String courseType, String grade, String courseTime) {
        this.courseId = courseId; this.studentId = studentId; this.courseName = courseName;
        this.courseType = courseType; this.grade = grade; this.courseTime = courseTime;
    }
}

public class StudentManagementGUI {
    private JFrame frame; // Main application frame
    private JTabbedPane tabbedPane; // Tab container for different features
    private JTextField studentNameField, studentIdField, courseIdField, courseNameField, gradeField;
    private JComboBox<String> studentTypeCombo, studentIdCombo, courseTypeCombo, courseDayCombo, courseTimeCombo, gradeStudentIdCombo, gradeCourseCombo, studentSelectorCombo;
    private JTextArea registeredStudentsArea, registeredCoursesArea, gradesArea;
    private DefaultTableModel tableModel; // Table model for displaying student courses

    private List<Student> students = new ArrayList<>(); // List to store registered students
    private List<StudentCourse> courses = new ArrayList<>(); // List to store registered courses

    // Strategy for grade evaluation
    private EvaluationStrategy evaluationStrategy = new AverageStrategy();

    public StudentManagementGUI() {
        frame = new JFrame("Student Management System"); // Initialize main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        tabbedPane = new JTabbedPane();
        initStudentRegistrationTab(); // Initialize "Student Registration" tab
        initCourseRegistrationTab(); // Initialize "Course Registration" tab
        initGradeAssignmentTab(); // Initialize "Assign Grades" tab
        initStudentCourseDisplayTab(); // Initialize "Student Courses" tab

        frame.add(tabbedPane); // Add tabs to frame
        frame.setVisible(true);
    }

    // Setup the "Student Registration" tab
private void initStudentRegistrationTab() {
    JPanel panel = new JPanel(new BorderLayout());
    JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

    // Input fields for student registration
    studentIdField = new JTextField(); // Make sure this comes first
    studentNameField = new JTextField();
    studentTypeCombo = new JComboBox<>(new String[]{"Undergraduate", "Graduate", "Part-Time"});
    JButton registerButton = new JButton("Register Student");
    registerButton.addActionListener(e -> registerStudent());

    // Ensure studentIdField only accepts digits
    ((AbstractDocument) studentIdField.getDocument()).setDocumentFilter(new DocumentFilter() {
        
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text.matches("[0-9]*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
            if (text.matches("[0-9]*")) {
                super.insertString(fb, offset, text, attrs);
            }
        }
    });

    // Add components to input panel
    inputPanel.add(new JLabel("Student ID:")); // Student ID first
    inputPanel.add(studentIdField);
    inputPanel.add(new JLabel("Student Name:"));
    inputPanel.add(studentNameField);
    inputPanel.add(new JLabel("Student Type:"));
    inputPanel.add(studentTypeCombo);
    inputPanel.add(registerButton);
    JButton deleteStudentButton = new JButton("Delete Student");
    deleteStudentButton.addActionListener(e -> deleteStudent());
    inputPanel.add(deleteStudentButton);

    // Display area for registered students
    registeredStudentsArea = new JTextArea();
    
    // Table to show discounts for different student types
    String[] columnNames = {"Student Type", "Discount Percentage"};
    Object[][] data = {
        {"Graduate", "15%"},
        {"Undergraduate", "30%"},
        {"Part-Time", "10%"}
    };
    JTable discountTable = new JTable(data, columnNames);
    JScrollPane discountScrollPane = new JScrollPane(discountTable);
    
    // Add input panel, student registration area, and discount table to the main panel
    panel.add(inputPanel, BorderLayout.NORTH);
    panel.add(new JScrollPane(registeredStudentsArea), BorderLayout.CENTER);
    panel.add(discountScrollPane, BorderLayout.SOUTH);  // Add the discount table at the bottom
    
    tabbedPane.addTab("Student Registration", panel);
}

   // Setup the "Course Registration" tab
private void initCourseRegistrationTab() {
    JPanel panel = new JPanel(new BorderLayout());
    JPanel inputPanel = new JPanel(new GridLayout(8, 2, 2, 10));

    // Input fields for course registration
    courseIdField = new JTextField(); 
    courseNameField = new JTextField();
    courseTypeCombo = new JComboBox<>(new String[]{"Core", "Elective", "Lab"});
    studentIdCombo = new JComboBox<>();
    courseDayCombo = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});
    courseTimeCombo = new JComboBox<>(new String[]{"9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM"});

    // Button actions for course registration and deletion
    JButton registerCourseButton = new JButton("Register Course");
    registerCourseButton.addActionListener(e -> registerCourse()); // Button for registering a course
    JButton deleteCourseButton = new JButton("Delete Course");
    deleteCourseButton.addActionListener(e -> deleteCourse()); // Button for deleting a course

    // Add components to input panel
    inputPanel.add(new JLabel("Course ID:")); inputPanel.add(courseIdField);
    inputPanel.add(new JLabel("Course Name:")); inputPanel.add(courseNameField);
    inputPanel.add(new JLabel("Course Type:")); inputPanel.add(courseTypeCombo);
    inputPanel.add(new JLabel("Student ID:")); inputPanel.add(studentIdCombo);
    inputPanel.add(new JLabel("Course Day:")); inputPanel.add(courseDayCombo);
    inputPanel.add(new JLabel("Course Time:")); inputPanel.add(courseTimeCombo);
    inputPanel.add(registerCourseButton); inputPanel.add(deleteCourseButton);
    
    // Label to display course pricing information in a table format
    JLabel coursePriceInfoLabel = new JLabel("<html><table border='2' cellpadding='5'><tr><th>Course Type</th><th>Price</th></tr>"
    + "<tr><td>Core Course</td><td>$500</td></tr>"
    + "<tr><td>Elective Course</td><td>$350</td></tr>"
    + "<tr><td>Lab Course</td><td>$900</td></tr>"
    + "</table></html>");

    // Add the pricing table label to the bottom of the panel
    panel.add(coursePriceInfoLabel, BorderLayout.SOUTH);

    // Display area for registered courses
    registeredCoursesArea = new JTextArea();
    panel.add(inputPanel, BorderLayout.NORTH);
    panel.add(new JScrollPane(registeredCoursesArea), BorderLayout.CENTER);
    
    // Add the course registration tab to the tabbed pane
    tabbedPane.addTab("Course Registration", panel);
}


// Setup the "Assign Grades" tab
private void initGradeAssignmentTab() {
    JPanel panel = new JPanel(new BorderLayout());
    JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

    // Input fields for grade assignment
    gradeStudentIdCombo = new JComboBox<>(); gradeCourseCombo = new JComboBox<>();
    gradeField = new JTextField();
    JButton assignGradeButton = new JButton("Assign Grade");
    assignGradeButton.addActionListener(e -> assignGrade()); // Button for assigning grades

    // Add components to input panel
    inputPanel.add(new JLabel("Student ID:")); inputPanel.add(gradeStudentIdCombo);
    inputPanel.add(new JLabel("Course Name:")); inputPanel.add(gradeCourseCombo);
    inputPanel.add(new JLabel("Grade:")); inputPanel.add(gradeField);
    inputPanel.add(assignGradeButton); // Button to assign grade

    // Display area for assigned grades
    gradesArea = new JTextArea();
    panel.add(inputPanel, BorderLayout.NORTH);
    panel.add(new JScrollPane(gradesArea), BorderLayout.CENTER);
    
    // Add the grade assignment tab to the tabbed pane
    tabbedPane.addTab("Assign Grades", panel);
}

// Setup the "Student Courses" tab
private void initStudentCourseDisplayTab() {
    JPanel panel = new JPanel(new BorderLayout());
    JPanel topPanel = new JPanel(new BorderLayout());

    // Dropdown to select a student for viewing their courses
    studentSelectorCombo = new JComboBox<>();
    studentSelectorCombo.addActionListener(e -> refreshStudentCoursesTable()); // Update table based on selected student
    topPanel.add(new JLabel("Select Student: "), BorderLayout.WEST);
    topPanel.add(studentSelectorCombo, BorderLayout.CENTER);

    // Button to calculate average grade for the selected student
    JButton calculateAverageButton = new JButton("Calculate Average");
    calculateAverageButton.addActionListener(e -> calculateAverage());
    topPanel.add(calculateAverageButton, BorderLayout.EAST);

    panel.add(topPanel, BorderLayout.NORTH);

    // Table to display courses and grades for the selected student
    tableModel = new DefaultTableModel(new String[]{"Course ID", "Course Name", "Course Type", "Course Time", "Grade", "Cost"}, 0);
    JTable studentCourseTable = new JTable(tableModel);
    panel.add(new JScrollPane(studentCourseTable), BorderLayout.CENTER);
    
    // Add the student courses display tab to the tabbed pane
    tabbedPane.addTab("Student Courses", panel);
}

// Registers a new student
private void registerStudent() {
    String name = studentNameField.getText(), id = studentIdField.getText();
    // Validate student registration
    if (name.isEmpty() || id.isEmpty() || students.stream().anyMatch(s -> s.id.equals(id))) {
        JOptionPane.showMessageDialog(frame, "Please enter all fields and ensure the ID is unique", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Add student to list and update combo boxes and display area
    students.add(new Student(id, name, (String) studentTypeCombo.getSelectedItem()));
    studentIdCombo.addItem(id); gradeStudentIdCombo.addItem(id); studentSelectorCombo.addItem(id);
    registeredStudentsArea.append("Name : " + name + " -- " + "ID : " + id + " -- " + "Type : " + studentTypeCombo.getSelectedItem() + "\n");
    studentNameField.setText(""); studentIdField.setText(""); studentTypeCombo.setSelectedIndex(0);
    JOptionPane.showMessageDialog(frame, "Student registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
}

// Deletes a student by ID
private void deleteStudent() {
    String idToDelete = studentIdField.getText();
    if (idToDelete.isEmpty()) { 
        JOptionPane.showMessageDialog(frame, "Please enter the student ID to delete", "Error", JOptionPane.ERROR_MESSAGE); 
        return; 
    }

    // Remove student from list and update combo boxes and display area
    students.removeIf(student -> student.id.equals(idToDelete));
    studentIdCombo.removeItem(idToDelete); gradeStudentIdCombo.removeItem(idToDelete); studentSelectorCombo.removeItem(idToDelete);
    updateRegisteredStudentsArea();
    studentIdField.setText(""); 
    JOptionPane.showMessageDialog(frame, "Student deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
}


// A list of courses already registered by students
private List<String> registeredCourseIds = new ArrayList<>();

// Registers a new course for a student
private void registerCourse() {
    String courseId = courseIdField.getText();
    String courseName = courseNameField.getText();
    String studentId = (String) studentIdCombo.getSelectedItem();
    String courseDay = (String) courseDayCombo.getSelectedItem();
    String courseTime = (String) courseTimeCombo.getSelectedItem();
    String courseType = (String) courseTypeCombo.getSelectedItem();

    if (courseId.isEmpty() || courseName.isEmpty() || studentId == null || courseDay == null || courseTime == null || courseType == null) {
        JOptionPane.showMessageDialog(frame, "Please enter all fields", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Check if the courseId is a valid number
    try {
        Integer.parseInt(courseId); // Try to convert courseId to an integer
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(frame, "Course ID must be a number.", "Invalid Course ID", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Determine course cost based on the course type
    double baseCost = 0.0;
    String courseDescription = "";

    if (courseType.equals("Core")) {
        baseCost = 500.0;  // Core course cost
        courseDescription = "Core course";
    } else if (courseType.equals("Elective")) {
        baseCost = 350.0;  // Elective course cost
        courseDescription = "Elective course";
    } else if (courseType.equals("Lab")) {
        baseCost = 900.0;  // Lab course cost
        courseDescription = "Lab course";
    }

    // Apply discount based on student type
    double discount = 0.0;
    String studentType = students.stream().filter(s -> s.id.equals(studentId))
                                  .map(s -> s.type)
                                  .findFirst().orElse("Undergraduate");

    if (studentType.equals("Undergraduate")) {
        discount = 0.3;  // 30% discount for undergraduates
    } else if (studentType.equals("Graduate")) {
        discount = 0.15;  // 15% discount for graduate students
    } else if (studentType.equals("Part-Time")) {
        discount = 0.10;  // 10% discount for part-time students
    }

    // Apply discount to base cost
    double finalCost = baseCost * (1 - discount);

    // Add the course to the list of courses for the student
    courses.add(new StudentCourse(courseId, studentId, courseName, courseType, "N/A", courseDay + " " + courseTime));

    // Add course to the grade course combo
    gradeCourseCombo.addItem(courseName);

    // Update the course registration area
    tableModel.addRow(new Object[]{courseId, courseName, courseType, courseDay + " " + courseTime, "N/A", finalCost});
    registeredCoursesArea.append(courseId + " - " + courseName + " (" + courseType + ") - Cost: $" + finalCost + "\n");

    // Clear the input fields
    courseIdField.setText("");
    courseNameField.setText("");
    courseTypeCombo.setSelectedIndex(0);
    studentIdCombo.setSelectedIndex(0);
    courseDayCombo.setSelectedIndex(0);
    courseTimeCombo.setSelectedIndex(0);

    // Display discount message
    String discountMessage = String.format("Discount applied: %.0f%%", discount * 100);
    JOptionPane.showMessageDialog(frame, "Course registered successfully with cost: $" + finalCost + "\n" + discountMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
}




    // Deletes a course by ID
    private void deleteCourse() {
        String courseIdToDelete = courseIdField.getText();
        if (courseIdToDelete.isEmpty()) { JOptionPane.showMessageDialog(frame, "Please enter the course ID to delete", "Error", JOptionPane.ERROR_MESSAGE); return; }

        courses.removeIf(course -> course.courseId.equals(courseIdToDelete));
        updateRegisteredCoursesArea();
        courseIdField.setText(""); JOptionPane.showMessageDialog(frame, "Course deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Assigns a grade to a student's course
    // Assigns a grade to a student's course
private void assignGrade() {
    String studentId = (String) gradeStudentIdCombo.getSelectedItem(), 
           courseName = (String) gradeCourseCombo.getSelectedItem(),
           grade = gradeField.getText();

    if (studentId == null || courseName == null || grade.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Please enter all fields", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Find the course and update the grade
    courses.stream().filter(course -> course.studentId.equals(studentId) && course.courseName.equals(courseName))
        .forEach(course -> {
            course.grade = grade;  // Update grade
        });

    refreshStudentCoursesTable(); // Refresh the table to show the updated grade and price
    gradesArea.append(studentId + " - " + courseName + " : " + grade + "\n");
    gradeField.setText(""); 
    JOptionPane.showMessageDialog(frame, "Grade assigned successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
}

    
    // Calculates and displays the average grade for the selected student
private void calculateAverage() {
    String selectedStudentId = (String) studentSelectorCombo.getSelectedItem();
    if (selectedStudentId == null || selectedStudentId.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "No student selected", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<StudentCourse> selectedStudentCourses = new ArrayList<>();
    for (StudentCourse course : courses) {
        if (course.studentId.equals(selectedStudentId)) {
            selectedStudentCourses.add(course);
        }
    }

    if (selectedStudentCourses.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "No courses found for the selected student", "Information", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    double totalGrade = 0.0;
    int gradedCourses = 0;
    for (StudentCourse course : selectedStudentCourses) {
        try {
            double grade = Double.parseDouble(course.grade);
            totalGrade += grade;
            gradedCourses++;
        } catch (NumberFormatException e) {
            // Skip courses without a valid numeric grade
        }
    }

    if (gradedCourses == 0) {
        JOptionPane.showMessageDialog(frame, "No valid grades available for average calculation", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        double averageGrade = totalGrade / gradedCourses;
        JOptionPane.showMessageDialog(frame, "The average grade for student " + selectedStudentId + " is: " + averageGrade, "Average Grade", JOptionPane.INFORMATION_MESSAGE);
    }
}


    // Updates the table to display courses for the selected student
private void refreshStudentCoursesTable() {
    String studentId = (String) studentSelectorCombo.getSelectedItem();
    tableModel.setRowCount(0); // Clear existing table rows
    courses.stream().filter(course -> course.studentId.equals(studentId))
        .forEach(course -> {
            double courseCost = 0.0;
            if (course.courseType.equals("Core")) {
                courseCost = 500.0;  // Core course cost
            } else if (course.courseType.equals("Elective")) {
                courseCost = 350.0;  // Elective course cost
            } else if (course.courseType.equals("Lab")) {
                courseCost = 700.0;  // Lab course cost
            }

            // Get student type to apply discount
            String studentType = students.stream().filter(s -> s.id.equals(studentId))
                                          .map(s -> s.type)
                                          .findFirst().orElse("Undergraduate");

            double discount = 0.0;
            if (studentType.equals("Undergraduate")) {
                discount = 0.30;
            } else if (studentType.equals("Graduate")) {
                discount = 0.15;
            } else if (studentType.equals("Part-Time")) {
                discount = 0.10;
            }

            // Apply discount to course cost
            double discountedCost = courseCost * (1 - discount);
            tableModel.addRow(new Object[]{course.courseId, course.courseName, course.courseType, course.courseTime, course.grade, discountedCost});
        });
}

    // Updates the area displaying registered students
    private void updateRegisteredStudentsArea() {
        registeredStudentsArea.setText(""); students.forEach(student -> registeredStudentsArea.append(student.id + " - " + student.name + "\n"));
    }

    // Updates the area displaying registered courses
    private void updateRegisteredCoursesArea() {
        registeredCoursesArea.setText(""); courses.forEach(course -> registeredCoursesArea.append(course.courseId + " - " + course.studentId + " - " + course.courseName + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
        
    }
     
    }
