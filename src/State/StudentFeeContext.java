package state;

// Context class for managing fee calculation based on a student's fee state
public class StudentFeeContext {
    private FeeState currentState; // The current fee state

    // Constructor to initialize the context with a default state (e.g., UndergraduateState)
    public StudentFeeContext() {
        this.currentState = new UndergraduateState(); // أو أي حالة افتراضية أخرى
    }

    // Method to set the current fee state
    public void setFeeState(FeeState state) {
        this.currentState = state;
    }

    // Method to calculate the fee using the current state's logic
    public double calculateFee(double baseFee) {
        if (currentState == null) {
            throw new IllegalStateException("Fee state not set");
        }
        return currentState.calculateFee(baseFee);
    }
}
