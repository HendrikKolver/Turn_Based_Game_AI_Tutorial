package neuralnetwork;

public class HiddenLayerNeuron {
    private double value = 0;

    public void addValue(double value){
        this.value += value;
    }

    public double fire(){
        //todo sigmoid activation function here
        return value;
    }
}
