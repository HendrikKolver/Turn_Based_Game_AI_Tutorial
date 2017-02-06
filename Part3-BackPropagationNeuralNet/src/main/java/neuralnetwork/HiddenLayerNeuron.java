package neuralnetwork;

public class HiddenLayerNeuron extends Neuron {
    public double fire(){
        valueBeforeFire = calculateSigmoid();
        value = 0;
        return valueBeforeFire;
    }
}
