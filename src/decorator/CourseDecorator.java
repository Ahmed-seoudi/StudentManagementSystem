package decorator;

public abstract class CourseDecorator implements Course {
    protected Course decoratedCourse; // The course being decorated

    // Constructor to initialize the decorator with the course to be decorated
    public CourseDecorator(Course decoratedCourse) {
        this.decoratedCourse = decoratedCourse;
    }

    // Method to get the description of the decorated course
    @Override
    public String getDescription() {
        return decoratedCourse.getDescription();
    }

    // Method to get the cost of the decorated course
    @Override
    public double getCost() {
        return decoratedCourse.getCost();
    }
}
