package strategy;

public interface EvaluationStrategy {
    
    // Method to evaluate the grades (to be implemented by concrete strategies)
    double evaluate(double[] grades);
}
