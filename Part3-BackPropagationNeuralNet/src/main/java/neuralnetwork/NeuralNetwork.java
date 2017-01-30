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
    private Random random = new Random();


    public NeuralNetwork(int hiddenLayerNeuronCount){
        inputLayer = new ArrayList<>();
        hiddenLayer = new ArrayList<>();
        inputToHiddenLayerWeights = new ArrayList<>();
        hiddenToOutputLayerWeights = new ArrayList<>();
        this.hiddenLayerNeuronCount = hiddenLayerNeuronCount;
    }

    public void init(){
        //input layer
        for (int i = 0; i < 9; i++) {
            inputLayer.add(new InputLayerNeuron());
        }

        //hidden layer
        for (int i = 0; i < hiddenLayerNeuronCount; i++) {
            hiddenLayer.add(new HiddenLayerNeuron());
        }

        //output layer
        outputNeuron = new OutputLayerNeuron();

        //weights between input and hidden layer
        for (int i = 0; i < inputLayer.size() * hiddenLayer.size(); i++) {
            inputToHiddenLayerWeights.add(getRandomDouble());
        }

        //weights between hidden and output layer
        for (int i = 0; i < hiddenLayer.size(); i++) {
            hiddenToOutputLayerWeights.add(getRandomDouble());
        }
    }

    private double getRandomDouble() {
        return random.nextDouble();
    }

}

