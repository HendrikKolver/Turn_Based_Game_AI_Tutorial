package player;


import game.BoardState;
import game.GameStateValue;
import gametree.ABPruningTree;

public class RandomMovePlayer implements PlayerInterface {


    @Override
    public PlayerMove getMove(BoardState boardState, GameStateValue playerStateValue, GameStateValue opponentStateValue) {
        ABPruningTree ABPruningTree = new ABPruningTree(playerStateValue, opponentStateValue, boardState);
        return ABPruningTree.getOptimalMove();
    }
}
