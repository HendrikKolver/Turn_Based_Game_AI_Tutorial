package PSO;

import neuralnetwork.NeuralNetwork;
import player.NeuralNetPlayer;
import player.PlayerInterface;

import java.util.ArrayList;
import java.util.List;

public class PSO {
    private static final double LEARNING_FACTOR = 2;
    private NeuralNetPlayer globalBestPlayer;
    private int playerCount = 10;
    private int hiddenNeuroncount = 4;
    private int roundCount = 10;


    public PlayerInterface trainPlayer(){
        List<NeuralNetPlayer> players = getSeedPlayers();

        int roundCounter = 0;

        while(roundCounter < roundCount){
            Tournament.runTournament(players);
            updatePersonalBests(players);
            globalBestPlayer = getBestPlayer(players);
            updateVelocities(players, globalBestPlayer);
            updatePlayerPositions(players);

            //TODO clear the current player scores after every round
            clearCurrentScores();
        }

        //TODO play one last round of the game before deciding the winner
        return getBestPlayer(players);
    }

    private void updatePlayerPositions(List<NeuralNetPlayer> players) {
        for (NeuralNetPlayer player : players) {
            List<Double> velocities = player.getVelocity();
            NeuralNetwork currentSolution = player.getNeuralNetwork();
            int velocityCount = 0;

            for (int i = 0; i < currentSolution.getInputToHiddenLayerWeights().size(); i++) {
                currentSolution.getInputToHiddenLayerWeights().set(i, velocities.get(velocityCount)+ currentSolution.getInputToHiddenLayerWeights().get(i));
                velocityCount++;
            }

            for (int i = 0; i < currentSolution.getHiddenToOutputLayerWeights().size(); i++) {
                currentSolution.getHiddenToOutputLayerWeights().set(i, velocities.get(velocityCount)+ currentSolution.getHiddenToOutputLayerWeights().get(i));
                velocityCount++;
            }
        }
    }

    private void updateVelocities(List<NeuralNetPlayer> players, NeuralNetPlayer globalBestPlayer) {
       //calculate velocity for each player
        for (NeuralNetPlayer player : players) {
            List<Double> velocities = player.getVelocity();
            NeuralNetwork currentSolution = player.getNeuralNetwork();
            NeuralNetwork personalBestSolution = player.getPersonalBestNetwork();
            NeuralNetwork globalBestSolution = globalBestPlayer.getNeuralNetwork();

            //Calculate velocity for each dimension
            for (int i = 0; i < velocities.size(); i++) {
                double personalComponent = calculateComponent(personalBestSolution, currentSolution, i);
                double globalComponent = calculateComponent(globalBestSolution, currentSolution, i);
                velocities.set(i, velocities.get(i)+ personalComponent + globalComponent);
            }
        }
    }

    private double calculateComponent(NeuralNetwork comparingSolution, NeuralNetwork currentSolution, int i) {
        double comparingSolutionWeight;
        double currentSolutionWeight;
        if(i < currentSolution.getInputToHiddenLayerWeights().size()){
            comparingSolutionWeight = comparingSolution.getInputToHiddenLayerWeights().get(i);
            currentSolutionWeight = currentSolution.getInputToHiddenLayerWeights().get(i);
        }else{
            comparingSolutionWeight = comparingSolution.getHiddenToOutputLayerWeights().get(i- comparingSolution.getInputToHiddenLayerWeights().size());
            currentSolutionWeight = currentSolution.getHiddenToOutputLayerWeights().get(i - currentSolution.getInputToHiddenLayerWeights().size());
        }

        return ((comparingSolutionWeight - currentSolutionWeight) * LEARNING_FACTOR * Math.random());
    }

    private void updatePersonalBests(List<NeuralNetPlayer> players) {
        for (NeuralNetPlayer player : players) {
            if (player.getCurrentScore() > player.getPersonalBestScore()){
                player.updatePersonalBest();
            }
        }

    }

    private List<NeuralNetPlayer> getSeedPlayers() {
        List<NeuralNetPlayer> seedPlayers = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            seedPlayers.add(new NeuralNetPlayer(new NeuralNetwork(hiddenNeuroncount)));
        }
        return seedPlayers;
    }

    private NeuralNetPlayer getBestPlayer(List<NeuralNetPlayer> players){
        int maxScore = -100;
        NeuralNetPlayer winningPlayer = null;

        for (NeuralNetPlayer player : players) {
            if (player.getPersonalBestScore() > maxScore) {
                maxScore = player.getPersonalBestScore();
                winningPlayer = player;
            }
        }
        return winningPlayer;
    }

    public NeuralNetPlayer getGlobalBestPlayer() {
        return globalBestPlayer;
    }
}
