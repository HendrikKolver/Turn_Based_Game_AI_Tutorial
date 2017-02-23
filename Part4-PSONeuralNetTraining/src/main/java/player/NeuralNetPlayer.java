package player;


import PSO.Tournament;
import game.BoardState;
import game.GameEngine;
import game.GameStateValue;
import gametree.ABPruningTree;
import neuralnetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetPlayer implements PlayerInterface {
    private NeuralNetwork neuralNetwork;
    private int currentScore;
    private NeuralNetwork personalBestNetwork;
    private List<Double> velocity;


    public NeuralNetPlayer(NeuralNetwork nn){
        this.neuralNetwork = nn;
        this.personalBestNetwork = nn;
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
        //Play the current solution against the current personal best to determine if the current solution is better or not
        NeuralNetPlayer playerPersonalBest = new NeuralNetPlayer(personalBestNetwork.copy());
        NeuralNetPlayer currentPlayer = new NeuralNetPlayer(neuralNetwork.copy());
        List<NeuralNetPlayer> players = new ArrayList<>();
        players.add(playerPersonalBest);
        players.add(currentPlayer);

        Tournament.runTournament(players);

        if(currentPlayer.getCurrentScore() > playerPersonalBest.getCurrentScore()){
            //Using a copy of the neural network as it's weights will keep getting updated as the training continues
            personalBestNetwork = neuralNetwork.copy();
        }
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
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

    public NeuralNetwork getPersonalBestNetwork() {
        return personalBestNetwork;
    }

    public List<Double> getVelocity() {
        return velocity;
    }
}
