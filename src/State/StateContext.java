package state;

// Context class for managing and delegating fee calculation to the current state
public class StateContext {
    private FeeState currentState; // The current fee state

    // Constructor to initialize the context with an initial state
    public StateContext(FeeState initialState) {
        currentState = initialState;
    }

    // Method to change the current state
    public void setState(FeeState newState) {
        currentState = newState;
    }

    // Method to calculate the fee using the current state's logic
    public double calculateFee(double baseFee) {
        return currentState.calculateFee(baseFee);
    }
}
