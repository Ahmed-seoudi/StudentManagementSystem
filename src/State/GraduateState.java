package state;

// Class representing the fee calculation for graduate students
public class GraduateState implements FeeState {

    @Override
    public double calculateFee(double baseFee) {
        // Apply a 15% discount for graduates
        return baseFee * 0.85;
    }
}
