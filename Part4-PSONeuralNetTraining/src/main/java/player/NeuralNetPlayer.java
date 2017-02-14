package player;


import game.BoardState;
import game.GameStateValue;
import gametree.ABPruningTree;
import neuralnetwork.NeuralNetwork;

public class NeuralNetPlayer implements PlayerInterface {
    private NeuralNetwork neuralNetwork;
    private int score;

    public NeuralNetPlayer(NeuralNetwork nn){
        this.neuralNetwork = nn;
    }

    @Override
    public PlayerMove getMove(BoardState boardState, GameStateValue playerStateValue, GameStateValue opponentStateValue) {
        ABPruningTree ABPruningTree = new ABPruningTree(playerStateValue, opponentStateValue, boardState, neuralNetwork);
        return ABPruningTree.getOptimalMove();
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public void wonGame(){
        score++;
    }

    public void lostGame(){
        score --;
    }

    public int getScore(){
        return score;
    }
}
