package gametree;

import game.BoardState;
import game.GameStateValue;
import player.PlayerMove;

import java.util.List;

public class MinMaxTree {

    private final BoardState boardState;
    private final GameStateValue playerState;
    private final GameStateValue opponentState;
    private final int MAX_PLY_DEPTH = 50;

    public MinMaxTree(GameStateValue player, GameStateValue opponent, BoardState boardState){
        this.boardState = boardState;
        this.playerState = player;
        this.opponentState = opponent;
    }

    public PlayerMove getOptimalMove(){
        GameTreeNode rootNode = new GameTreeNode(opponentState, playerState, boardState);
        buildTreeMax(rootNode, 0);
        List<GameTreeNode> children = rootNode.getChildren();


        GameTreeNode winningChild = null;
        double evalScoreMax = -100;
        for (GameTreeNode child: children) {
            if(child.getEvalScore() > evalScoreMax){
                winningChild = child;
                evalScoreMax = child.getEvalScore();
            }
        }

        return winningChild.getMove();
    }

    private double buildTreeMax(GameTreeNode rootNode, int plyDepth){
        if(rootNode.isEndState() || plyDepth >= MAX_PLY_DEPTH){
            rootNode.evalBoardState();
            return rootNode.getEvalScore();
        }

        rootNode.generateChildren();
        List<GameTreeNode> nodes = rootNode.getChildren();

        double evalScore = -100;
        for (GameTreeNode node: nodes) {
            double nodeScore = buildTreeMin(node, plyDepth+1);
            node.setEvalScore(nodeScore);
            if(nodeScore > evalScore){
                evalScore = nodeScore;
            }
        }

        return evalScore;
    }

    private double buildTreeMin(GameTreeNode rootNode, int plyDepth){
        if(rootNode.isEndState() || plyDepth >= MAX_PLY_DEPTH){
            rootNode.evalBoardState();
            return rootNode.getEvalScore();
        }

        rootNode.generateChildren();
        List<GameTreeNode> nodes = rootNode.getChildren();

        double evalScore = 100;
        for (GameTreeNode node: nodes) {
            double nodeScore = buildTreeMax(node, plyDepth+1);
            node.setEvalScore(nodeScore);
            if(nodeScore < evalScore){
                evalScore = nodeScore;
            }
        }

        return evalScore;
    }
}
