package gametree;

import game.BoardState;
import game.GameEngine;
import game.GameStateValue;
import player.PlayerMove;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameTreeNode {
    private GameStateValue stateValue;
    private double evalScore;
    private List<GameTreeNode> children;
    private BoardState nodeBoardState;
    private PlayerMove move;

    public GameTreeNode(GameStateValue stateValue, BoardState boardState){
        this.stateValue = stateValue;
        this.nodeBoardState = boardState;
        this.children = new ArrayList<>();
    }

    public void evalBoardState(){
        evalScore = ThreadLocalRandom.current().nextInt(0, 20);
    }

    public void generateChildren() {
        GameStateValue childValue = getChildGameStateValue();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                BoardState newState = nodeBoardState.clone();
                boolean isValidMove = newState.makeMove(childValue, row, col);
                if (isValidMove) {
                    GameTreeNode newNode = new GameTreeNode(childValue, newState);
                    newNode.setMove(new PlayerMove(row, col));
                    children.add(newNode);
                }
            }
        }
    }

    private GameStateValue getChildGameStateValue() {
        if(stateValue.equals(GameStateValue.CROSS)){
            return GameStateValue.NAUGHT;
        }

        return GameStateValue.CROSS;
    }

    public List<GameTreeNode> getChildren() {
        return children;
    }

    public boolean isEndState() {
        GameEngine engine = new GameEngine(null, null);
        return engine.isGameFinished(nodeBoardState);
    }

    public double getEvalScore() {
        return evalScore;
    }

    public void setEvalScore(double evalScore) {
        this.evalScore = evalScore;
    }

    public PlayerMove getMove() {
        return move;
    }

    public void setMove(PlayerMove move) {
        this.move = move;
    }
}
