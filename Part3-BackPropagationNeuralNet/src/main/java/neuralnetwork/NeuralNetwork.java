package neuralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    private List<HiddenLayerNeuron> hiddenLayer;
    private OutputLayerNeuron outputNeuron;
    private List<Double> inputToHiddenLayerWeights;
    private List<Double> hiddenToOutputLayerWeights;
    private final int hiddenLayerNeuronCount;
    private final int inputCount = 9;
    private Random random = new Random();
    private final double learningRate = 0.02;
    private List<Double> inputValues;


    public NeuralNetwork(int hiddenLayerNeuronCount) {
        hiddenLayer = new ArrayList<>();
        inputToHiddenLayerWeights = new ArrayList<>();
        hiddenToOutputLayerWeights = new ArrayList<>();
        this.hiddenLayerNeuronCount = hiddenLayerNeuronCount;
        init();

    }

    public void init() {
        //hidden layer
        for (int i = 0; i < hiddenLayerNeuronCount; i++) {
            hiddenLayer.add(new HiddenLayerNeuron());
        }

        //output layer
        outputNeuron = new OutputLayerNeuron();

        //weights between input and hidden layer
        for (int i = 0; i < inputCount * hiddenLayer.size(); i++) {
            inputToHiddenLayerWeights.add(getRandomDouble());
        }

        //weights between hidden and output layer
        for (int i = 0; i < hiddenLayer.size(); i++) {
            hiddenToOutputLayerWeights.add(getRandomDouble());
        }
    }

    public double calculate(List<Double> inputValues) {
        int currentWeightCounter = 0;
        this.inputValues = inputValues;

        //fireInputs
        for (int inputCounter = 0; inputCounter < inputCount; inputCounter++) {
            for (int hiddenLayerCounter = 0; hiddenLayerCounter < hiddenLayerNeuronCount; hiddenLayerCounter++) {
                HiddenLayerNeuron hiddenLayerNeuron = hiddenLayer.get(hiddenLayerCounter);
                hiddenLayerNeuron.addValue(inputValues.get(inputCounter) * inputToHiddenLayerWeights.get(currentWeightCounter));
                currentWeightCounter++;
            }
        }

        currentWeightCounter = 0;
        //fireHiddenLayer
        for (int hiddenLayerCounter = 0; hiddenLayerCounter < hiddenLayerNeuronCount; hiddenLayerCounter++) {
            outputNeuron.addValue(hiddenLayer.get(hiddenLayerCounter).fire() * hiddenToOutputLayerWeights.get(currentWeightCounter));
            currentWeightCounter++;
        }

        return outputNeuron.fire();
    }

    public void trainNetwork() {
        TrainingData trainingData = new TrainingData();
        List<TrainingDataItem> trainingItems = trainingData.getTrainingItems();


        for (int i = 0; i < 2000; i++) {


            for (TrainingDataItem trainingItem : trainingItems) {
                List<Double> newHiddenToOutputWeights = new ArrayList<>();
                List<Double> newInputToHiddenWeights = new ArrayList<>();
                double output = calculate(trainingItem.getValues());
                System.out.println(squaredError(output, trainingItem.getExpectedResult()));

                int outputWeightCounter = 0;
                for (int hiddenLayerCounter = 0; hiddenLayerCounter < hiddenLayerNeuronCount; hiddenLayerCounter++) {
                    double hiddenOutput = hiddenLayer.get(hiddenLayerCounter).getValueBeforeFire();
                    double hiddenToOutputWeight = hiddenToOutputLayerWeights.get(outputWeightCounter);
                    double newWeight = outputToHiddenBackPropogate(trainingItem.getExpectedResult(), output, hiddenOutput, hiddenToOutputWeight);
                    newHiddenToOutputWeights.add(newWeight);
                    outputWeightCounter++;
                }

                int inputWeightCounter = 0;
                for (int inputCounter = 0; inputCounter < inputCount; inputCounter++) {
                    for (int hiddenLayerCounter = 0; hiddenLayerCounter < hiddenLayerNeuronCount; hiddenLayerCounter++) {
                        double difference = differenceBetweenExpectedAndActualResult(trainingItem.getExpectedResult(), output);
                        double partialDerivativeFinalOutput = partialDerivative(output);
                        double errorValue = difference * partialDerivativeFinalOutput;
                        double errorValueTimesWeight = errorValue * hiddenToOutputLayerWeights.get(hiddenLayerCounter);
                        double partialDerivativeHiddenOutput = partialDerivative(hiddenLayer.get(hiddenLayerCounter).getValueBeforeFire());
                        double calculatedValueForInput = inputValues.get(inputCounter) * errorValueTimesWeight * partialDerivativeHiddenOutput;
                        double newWeight = inputToHiddenLayerWeights.get(inputWeightCounter) - (learningRate * calculatedValueForInput);
                        newInputToHiddenWeights.add(newWeight);
                        inputWeightCounter++;
                    }
                }
                hiddenToOutputLayerWeights = newHiddenToOutputWeights;
                inputToHiddenLayerWeights = newInputToHiddenWeights;
            }
        }
    }

    private double squaredError(double output, double expected) {
        double difference = expected - output;
        return 0.5 * (Math.pow(difference, 2));
    }

    private double outputToHiddenBackPropogate(double target, double output, double hiddenOutput, double weight) {
        double deltaRule = -1 * (target - output) * output * (1 - output) * hiddenOutput;
        return weight - deltaRule * learningRate;
    }

    private double differenceBetweenExpectedAndActualResult(double expected, double actual) {
        return (actual - expected);
    }

    private double partialDerivative(double output) {
        return output * (1 - output);
    }

    private double getRandomDouble() {
        return random.nextDouble();
    }

}

