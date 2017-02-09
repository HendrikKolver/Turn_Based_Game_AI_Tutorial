package neuralnetwork;


public class OutputLayerNeuron extends Neuron  {
    public double fire(){
        double returnValue = calculateSigmoid();
        value = 0;
        return returnValue;
    }
}
