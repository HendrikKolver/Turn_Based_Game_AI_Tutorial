package neuralnetwork;

import java.util.List;

public class TrainingDataItem {
    private final List<Double> values;
    private final double expectedResult;

    public TrainingDataItem(List<Double> values, double expectedResult){
        this.values = values;
        this.expectedResult = expectedResult;
    }

    public List<Double> getValues() {
        return values;
    }

    public double getExpectedResult() {
        return expectedResult;
    }
}
