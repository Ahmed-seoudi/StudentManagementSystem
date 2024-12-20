package state;

// Context class for managing fee calculation based on a student's fee state
public class StudentFeeContext {
    private FeeState currentState; // The current fee state

    // Method to set the current fee state
    public void setFeeState(FeeState state) {
        this.currentState = state;
    }

    // Method to calculate the fee using the current state's logic
    public double calculateFee(double baseFee) {
        // Ensure a fee state is set before calculation
        if (currentState == null) {
            throw new IllegalStateException("Fee state not set");
        }
        return currentState.calculateFee(baseFee);
    }
}
