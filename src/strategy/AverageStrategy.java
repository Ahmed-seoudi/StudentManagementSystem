package strategy;

public class AverageStrategy implements EvaluationStrategy {
    
    // Method to evaluate the average of the grades
    @Override
    public double evaluate(double[] grades) {
        double sum = 0.0;
        
        // Loop through each grade and add it to the sum
        for (double grade : grades) {
            sum += grade;
        }
        
        // Return the average by dividing the sum by the number of grades
        return sum / grades.length;
    }
}
