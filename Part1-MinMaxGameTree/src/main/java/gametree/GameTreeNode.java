package gametree;

import game.BoardState;
import game.GameEngine;
import game.GameEngineHelper;
import game.GameStateValue;
import player.PlayerMove;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameTreeNode {
    private GameStateValue stateValue;
    private GameStateValue maxState;
    private double evalScore;
    private List<GameTreeNode> children;
    private BoardState nodeBoardState;
    private PlayerMove move;

    public GameTreeNode(GameStateValue stateValue, GameStateValue maxState, BoardState boardState){
        this.stateValue = stateValue;
        this.nodeBoardState = boardState;
        this.children = new ArrayList<>();
        this.maxState = maxState;
    }

    //Always evaluate from the max perspective
    public void evalBoardState(){
        GameStateValue winningState = GameEngineHelper.getWinner(nodeBoardState);
        //draw state
        if(winningState == null){
            evalScore = 0;
            return;
        }

        //winning state
        if(winningState == maxState){
            evalScore = 10;
            return;
        }

        //losing state
        evalScore = -10;
    }

    public void generateChildren() {
        GameStateValue childValue = getChildGameStateValue();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                BoardState newState = nodeBoardState.clone();
                boolean isValidMove = newState.makeMove(childValue, row, col);
                if (isValidMove) {
                    GameTreeNode newNode = new GameTreeNode(childValue, maxState, newState);
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
        return GameEngineHelper.isGameFinished(nodeBoardState);
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
