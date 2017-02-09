package neuralnetwork;

public class Neuron {
    protected double value = 0;
    protected double valueBeforeFire = 0;

    //The sigmoid function is used to "squash" the output value of a neuron
    protected double calculateSigmoid(){
        return (1/( 1 + Math.pow(Math.E,(-1*value))));
    }

    public void addValue(double value){
        this.value += value;
    }

    public double getValueBeforeFire() {
        return valueBeforeFire;
    }
}
