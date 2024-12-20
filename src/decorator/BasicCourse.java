package decorator;

public class BasicCourse implements Course {
    private String description; // Description of the basic course
    private double cost; // Cost of the basic course

    // Constructor to initialize the BasicCourse with description and cost
    public BasicCourse(String description, double cost) {
        this.description = description;
        this.cost = cost;
    }

    // Method to get the description of the course
    @Override
    public String getDescription() {
        return description;
    }

    // Method to get the cost of the course
    @Override
    public double getCost() {
        return cost;
    }
}
