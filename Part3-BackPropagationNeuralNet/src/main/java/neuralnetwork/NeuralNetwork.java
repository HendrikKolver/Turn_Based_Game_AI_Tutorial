package neuralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    //Neurons
    private List<Double> inputValues;
    private List<HiddenLayerNeuron> hiddenLayer;
    private OutputLayerNeuron outputNeuron;
    private final int inputCount = 9;
    private final int hiddenLayerNeuronCount;

    //Connections between neurons
    private List<Double> inputToHiddenLayerWeights;
    private List<Double> hiddenToOutputLayerWeights;

    //Constants
    //The learning rate determines how quickly the network reaches an optimal value.
    private final double learningRate = 0.02;
    private final int TRAINING_ITERATIONS = 2000;
    private final Random RANDOM = new Random();

    public NeuralNetwork(int hiddenLayerNeuronCount) {
        this.hiddenLayerNeuronCount = hiddenLayerNeuronCount;
        init();
    }

    public void init() {
        //hidden layer object initialization
        hiddenLayer = new ArrayList<>();
        for (int i = 0; i < hiddenLayerNeuronCount; i++) {
            hiddenLayer.add(new HiddenLayerNeuron());
        }

        //output neuron object initialization
        outputNeuron = new OutputLayerNeuron();

        //weights between input and hidden layer initialization to RANDOM value between 0 and 1
        inputToHiddenLayerWeights = new ArrayList<>();
        for (int i = 0; i < inputCount * hiddenLayer.size(); i++) {
            inputToHiddenLayerWeights.add(getRandomDouble());
        }

        //weights between hidden and output layer initialization to RANDOM value between 0 and 1
        hiddenToOutputLayerWeights = new ArrayList<>();
        for (int i = 0; i < hiddenLayer.size(); i++) {
            hiddenToOutputLayerWeights.add(getRandomDouble());
        }
    }

    public double calculate(List<Double> inputValues) {
        this.inputValues = inputValues;

        //Iterate through every input and add its weighted value to every hidden layer neuron
        int inputToHiddenLayerWeightCounter = 0;
        for (int inputCounter = 0; inputCounter < inputCount; inputCounter++) {
            for (int hiddenLayerCounter = 0; hiddenLayerCounter < hiddenLayerNeuronCount; hiddenLayerCounter++) {

                HiddenLayerNeuron hiddenLayerNeuron = hiddenLayer.get(hiddenLayerCounter);
                double weightedValue = inputValues.get(inputCounter) * inputToHiddenLayerWeights.get(inputToHiddenLayerWeightCounter);
                hiddenLayerNeuron.addValue(weightedValue);

                inputToHiddenLayerWeightCounter++;
            }
        }

        //Iterate through every hidden layer neuron and add its weighted value to the output neuron
        int hiddenToOutputLayerWeightCounter = 0;
        for (int hiddenLayerCounter = 0; hiddenLayerCounter < hiddenLayerNeuronCount; hiddenLayerCounter++) {
            HiddenLayerNeuron hiddenLayerNeuron = hiddenLayer.get(hiddenLayerCounter);
            double weightedValue = hiddenLayerNeuron.fire() * hiddenToOutputLayerWeights.get(hiddenToOutputLayerWeightCounter);
            outputNeuron.addValue(weightedValue);

            hiddenToOutputLayerWeightCounter++;
        }

        //return the final calculated value of the output neuron as the result of the network
        return outputNeuron.fire();
    }

    public void trainNetwork() {

        List<TrainingDataItem> trainingItems = TrainingData.getTrainingItems();

        for (int trainingIteration = 0; trainingIteration < TRAINING_ITERATIONS; trainingIteration++) {

            //We complete a full back propagation cycle for every training data item
            for (TrainingDataItem trainingItem : trainingItems) {

                //Copies are made for all of the weights since weights are only updated after the back propagation is completed on the entire neural network
                List<Double> adjustedHiddenToOutputWeights = new ArrayList<>();
                List<Double> adjustedInputToHiddenWeights = new ArrayList<>();

                //Get the current value that the neural network returns for the training item
                double output = calculate(trainingItem.getValues());
                
                //Adjusting of the weights between the hidden and output layer
                int outputWeightCounter = 0;
                for (int hiddenLayerCounter = 0; hiddenLayerCounter < hiddenLayerNeuronCount; hiddenLayerCounter++) {
                    //Get the value that the hidden layer neuron had before it added it's weighted value to the output neuron. This value does not contain the weight
                    double hiddenOutput = hiddenLayer.get(hiddenLayerCounter).getValueBeforeFire();

                    //Get the weight that attaches this hidden layer neuron the the output neuron
                    double hiddenToOutputWeight = hiddenToOutputLayerWeights.get(outputWeightCounter);

                    //Get and store the new weight value
                    double newWeight = outputToHiddenBackPropogate(trainingItem.getExpectedResult(), output, hiddenOutput, hiddenToOutputWeight);
                    adjustedHiddenToOutputWeights.add(newWeight);

                    outputWeightCounter++;
                }

                //Adjusting the wieghts between the input and hidden layer
                int inputWeightCounter = 0;
                for (int inputCounter = 0; inputCounter < inputCount; inputCounter++) {
                    for (int hiddenLayerCounter = 0; hiddenLayerCounter < hiddenLayerNeuronCount; hiddenLayerCounter++) {
                        //Calculating impact the current weight has on the difference between the actual and expected output values for the training item
                        double difference = differenceBetweenExpectedAndActualResult(trainingItem.getExpectedResult(), output);
                        double partialDerivativeForNetworkOutput = partialDerivative(output);
                        double errorValue = difference * partialDerivativeForNetworkOutput;
                        double errorValueTimesWeight = errorValue * hiddenToOutputLayerWeights.get(hiddenLayerCounter);
                        double partialDerivativeHiddenOutput = partialDerivative(hiddenLayer.get(hiddenLayerCounter).getValueBeforeFire());

                        //Calculate the new weight value
                        double calculatedValueForInput = inputValues.get(inputCounter) * errorValueTimesWeight * partialDerivativeHiddenOutput;
                        double newWeight = inputToHiddenLayerWeights.get(inputWeightCounter) - (learningRate * calculatedValueForInput);
                        adjustedInputToHiddenWeights.add(newWeight);

                        inputWeightCounter++;
                    }
                }
                //Update the neural network weights for next training cycle
                hiddenToOutputLayerWeights = adjustedHiddenToOutputWeights;
                inputToHiddenLayerWeights = adjustedInputToHiddenWeights;
            }

        }
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

    private double squaredError(double output, double expected) {
        double difference = expected - output;
        return 0.5 * (Math.pow(difference, 2));
    }

    private double getRandomDouble() {
        return RANDOM.nextDouble();
    }

}

