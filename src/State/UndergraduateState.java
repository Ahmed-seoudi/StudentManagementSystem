package state;

// Class representing the fee calculation for undergraduate students
public class UndergraduateState implements FeeState {

    @Override
    public double calculateFee(double baseFee) {
        // Apply a 30% discount for undergraduate students
        return baseFee * 0.70;
    }
}
