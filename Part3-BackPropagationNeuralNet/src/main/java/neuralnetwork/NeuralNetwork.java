package neuralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    private List<InputLayerNeuron> inputLayer;
    private List<HiddenLayerNeuron> hiddenLayer;
    private OutputLayerNeuron outputNeuron;
    private List<Double> inputToHiddenLayerWeights;
    private List<Double> hiddenToOutputLayerWeights;
    private final int hiddenLayerNeuronCount;
    private final int inputCount = 9;
    private Random random = new Random();


    public NeuralNetwork(int hiddenLayerNeuronCount){
        inputLayer = new ArrayList<>();
        hiddenLayer = new ArrayList<>();
        inputToHiddenLayerWeights = new ArrayList<>();
        hiddenToOutputLayerWeights = new ArrayList<>();
        this.hiddenLayerNeuronCount = hiddenLayerNeuronCount;
    }

    public void init(){
        inputLayer = new ArrayList<>();

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

    public double calculate(List<Double> inputValues){
        for (int i = 0; i < inputCount; i++) {
            inputLayer.add(new InputLayerNeuron(inputValues.get(i)));
        }
        int currentWeightCounter = 0;

        //fireInputs
        for (int inputCounter = 0; inputCounter < inputCount; inputCounter++) {
            for (int hiddenLayerCounter = 0; hiddenLayerCounter < hiddenLayerNeuronCount; hiddenLayerCounter++) {
                HiddenLayerNeuron hiddenLayerNeuron = hiddenLayer.get(hiddenLayerCounter);
                hiddenLayerNeuron.addValue(inputValues.get(inputCounter)*inputToHiddenLayerWeights.get(currentWeightCounter));
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

    private double getRandomDouble() {
        return random.nextDouble();
    }

}

