package player;


import game.BoardState;
import game.GameStateValue;
import gametree.ABPruningTree;
import neuralnetwork.NeuralNetwork;

public class NeuralNetPlayer implements PlayerInterface {


    @Override
    public PlayerMove getMove(BoardState boardState, GameStateValue playerStateValue, GameStateValue opponentStateValue, NeuralNetwork nn) {
        ABPruningTree ABPruningTree = new ABPruningTree(playerStateValue, opponentStateValue, boardState, nn);
        return ABPruningTree.getOptimalMove();
    }
}
