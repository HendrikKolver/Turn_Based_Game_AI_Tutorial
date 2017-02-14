package PSO;

import neuralnetwork.NeuralNetwork;
import player.NeuralNetPlayer;

import java.util.ArrayList;
import java.util.List;

public class PSO {
    private NeuralNetPlayer bestPlayer;
    private int playerCount = 10;
    private int hiddenNeuroncount = 4;
    private int roundCount = 10;

    public void trainPlayer(){
        List<NeuralNetPlayer> players = getSeedPlayers();

        int roundCounter = 0;

        while(roundCounter < roundCount){
            Tournament.runTournament(players);
            bestPlayer = getBestPlayer(players);
        }
    }

    private List<NeuralNetPlayer> getSeedPlayers() {
        List<NeuralNetPlayer> seedPlayers = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            seedPlayers.add(new NeuralNetPlayer(new NeuralNetwork(hiddenNeuroncount)));
        }
        return null;
    }

    private NeuralNetPlayer getBestPlayer(List<NeuralNetPlayer> players){
        int maxScore = -100;
        NeuralNetPlayer winningPlayer = null;

        for (NeuralNetPlayer player : players) {
            if (player.getScore() > maxScore) {
                maxScore = player.getScore();
                winningPlayer = player;
            }
        }
        return winningPlayer;
    }

    public NeuralNetPlayer getBestPlayer() {
        return bestPlayer;
    }
}
