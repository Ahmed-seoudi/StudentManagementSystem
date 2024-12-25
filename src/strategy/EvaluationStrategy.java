package strategy;

public interface EvaluationStrategy {
    
    // Method to evaluate the grades (to be implemented by concrete strategies)
    double evaluate(double[] grades);

    public void evaluate(String studentId, String courseName);

    public void addGrade(String studentId, String courseName, double parseDouble);

    public double getAverage(String studentId, String courseName);

    public double[] getGrades();
}
