package player;


import game.BoardState;
import game.GameStateValue;
import gametree.ABPruningTree;
import neuralnetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetPlayer implements PlayerInterface {
    private NeuralNetwork neuralNetwork;
    private int currentScore;
    private int personalBestScore;
    private NeuralNetwork personalBestNetwork;
    private List<Double> velocity;


    public NeuralNetPlayer(NeuralNetwork nn){
        this.neuralNetwork = nn;
        velocity = new ArrayList<>();
        for (int i = 0; i < neuralNetwork.getInputToHiddenLayerWeights().size(); i++) {
            velocity.add(0.0);
        }

        for (int i = 0; i < neuralNetwork.getHiddenToOutputLayerWeights().size(); i++) {
            velocity.add(0.0);
        }
    }

    @Override
    public PlayerMove getMove(BoardState boardState, GameStateValue playerStateValue, GameStateValue opponentStateValue) {
        ABPruningTree ABPruningTree = new ABPruningTree(playerStateValue, opponentStateValue, boardState, neuralNetwork);
        return ABPruningTree.getOptimalMove();
    }

    public void updatePersonalBest() {
        //Doing a deep copy here so that the personal best network doesn't get updated unintentionally
        personalBestNetwork = neuralNetwork.copy();
        personalBestScore = currentScore;

    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public void wonGame(){
        currentScore++;
    }

    public void lostGame(){
        currentScore--;
    }

    public int getCurrentScore(){
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getPersonalBestScore() {
        return personalBestScore;
    }

    public void setPersonalBestScore(int personalBestScore) {
        this.personalBestScore = personalBestScore;
    }

    public NeuralNetwork getPersonalBestNetwork() {
        return personalBestNetwork;
    }

    public void setPersonalBestNetwork(NeuralNetwork personalBestNetwork) {
        this.personalBestNetwork = personalBestNetwork;
    }

    public List<Double> getVelocity() {
        return velocity;
    }

    public void setVelocity(List<Double> velocity) {
        this.velocity = velocity;
    }
}
