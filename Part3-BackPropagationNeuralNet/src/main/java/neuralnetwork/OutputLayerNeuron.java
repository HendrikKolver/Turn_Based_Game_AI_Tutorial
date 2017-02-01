package neuralnetwork;


public class OutputLayerNeuron extends Neuron  {
    public double fire(){
        double returnValue = value;
        value = 0;
        return returnValue;
    }
}
