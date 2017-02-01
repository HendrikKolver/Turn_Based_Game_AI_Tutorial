package neuralnetwork;


public class InputLayerNeuron extends Neuron  {
    public InputLayerNeuron(double value){
        this.value = value;
    }

    public void setValue(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }
}
