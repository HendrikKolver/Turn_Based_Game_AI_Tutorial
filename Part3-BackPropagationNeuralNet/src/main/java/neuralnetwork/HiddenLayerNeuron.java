package neuralnetwork;

public class HiddenLayerNeuron extends Neuron {
    public double fire(){
        double returnValue = calculateSigmoid();
        value = 0;
        return returnValue;
    }
}
