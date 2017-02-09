package neuralnetwork;

public class HiddenLayerNeuron extends Neuron {
    public double fire(){
        //Store the value before it gets cleared
        valueBeforeFire = calculateSigmoid();
        value = 0;
        return valueBeforeFire;
    }
}
