package gametree;

import game.BoardState;
import game.GameEngine;
import game.GameEngineHelper;
import game.GameStateValue;
import neuralnetwork.NeuralNetwork;
import player.PlayerMove;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameTreeNode {
    private final NeuralNetwork neuralNetwork;
    private GameStateValue stateValue;
    private GameStateValue maxState;
    private double evalScore;
    private List<GameTreeNode> children;
    private BoardState nodeBoardState;
    private PlayerMove move;

    public GameTreeNode(GameStateValue stateValue, GameStateValue maxState, BoardState boardState, NeuralNetwork neuralNetwork){
        this.stateValue = stateValue;
        this.nodeBoardState = boardState;
        this.children = new ArrayList<>();
        this.maxState = maxState;
        this.neuralNetwork = neuralNetwork;
    }

    //Always evaluate from the max perspective
    public void evalBoardState(){
        List<Double> boardValue = nodeBoardState.getNumericBoardValue(maxState);
        evalScore = neuralNetwork.calculate(boardValue);
    }

    public void generateChildren() {
        GameStateValue childValue = getChildGameStateValue();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                BoardState newState = nodeBoardState.clone();
                boolean isValidMove = newState.makeMove(childValue, row, col);
                if (isValidMove) {
                    GameTreeNode newNode = new GameTreeNode(childValue, maxState, newState, neuralNetwork);
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
