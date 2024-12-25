package decorator;

public class BasicCourse implements Course {
    private String description;
    private double cost; // Cost of the basic course

    // Constructor to initialize the BasicCourse with description and cost
    public BasicCourse(String description) {
        this.description = description;
        // Assign cost based on the course type
        switch (description.toLowerCase()) {
            case "core":
                this.cost = 500.0;
                break;
            case "elective":
                this.cost = 400.0;
                break;
            case "lab":
                this.cost = 650.0;
                break;
            default:
                this.cost = 0.0; // Default cost if description doesn't match any known type
        }
    }

    @Override
    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }
}
