package state;

// Class representing the fee calculation for part-time students
public class PartTimeState implements FeeState {

    @Override
    public double calculateFee(double baseFee) {
        // Apply a 10% discount for part-time students
        return baseFee * 0.90;
    }
}
