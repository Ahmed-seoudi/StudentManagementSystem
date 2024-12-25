package decorator;

public abstract class CourseDecorator implements Course {
    protected Course decoratedCourse;

    public CourseDecorator(Course decoratedCourse) {
        this.decoratedCourse = decoratedCourse;
    }

    @Override
    public double getCost() {
        return decoratedCourse.getCost();
    }

    public abstract String getDescription();
}
