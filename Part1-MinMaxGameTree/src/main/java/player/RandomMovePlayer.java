package player;


import game.BoardState;
import game.GameStateValue;
import gametree.MinMaxTree;

public class RandomMovePlayer implements PlayerInterface {


    @Override
    public PlayerMove getMove(BoardState boardState, GameStateValue playerStateValue, GameStateValue opponentStateValue) {
        MinMaxTree minMaxTree = new MinMaxTree(playerStateValue, opponentStateValue, boardState);
        return minMaxTree.getOptimalMove();
    }
}
