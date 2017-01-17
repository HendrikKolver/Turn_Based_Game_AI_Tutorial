package gametree;

import game.BoardState;
import game.GameStateValue;
import player.PlayerMove;

import java.util.List;

public class ABPruningTree {

    private final BoardState boardState;
    private final GameStateValue playerState;
    private final GameStateValue opponentState;
    private final int MAX_PLY_DEPTH = 50;
    private final int MIN_CAP = -100;
    private final int MAX_CAP = 100;

    public ABPruningTree(GameStateValue player, GameStateValue opponent, BoardState boardState){
        this.boardState = boardState;
        this.playerState = player;
        this.opponentState = opponent;
    }

    public PlayerMove getOptimalMove(){
        GameTreeNode rootNode = new GameTreeNode(opponentState, playerState, boardState);
        buildTreeMax(rootNode, 0, MIN_CAP, MAX_CAP);
        List<GameTreeNode> children = rootNode.getChildren();


        GameTreeNode winningChild = null;
        double evalScoreMax = MIN_CAP;
        for (GameTreeNode child: children) {
            if(child.getEvalScore() > evalScoreMax){
                winningChild = child;
                evalScoreMax = child.getEvalScore();
            }
        }

        System.out.println(GlobalCounter.getCounter());
        return winningChild.getMove();
    }

    private double buildTreeMax(GameTreeNode rootNode, int plyDepth, double alpha, double beta){
        if(rootNode.isEndState() || plyDepth >= MAX_PLY_DEPTH){
            rootNode.evalBoardState();
            return rootNode.getEvalScore();
        }

        rootNode.generateChildren();
        List<GameTreeNode> nodes = rootNode.getChildren();

        for (GameTreeNode node: nodes) {
            GlobalCounter.increaseCounter();
            double nodeScore = buildTreeMin(node, plyDepth+1, alpha, beta);
            node.setEvalScore(nodeScore);
            if(nodeScore > alpha){
                alpha = nodeScore;
            }

            if(beta <= alpha){
                break;
            }
        }

        return alpha;
    }

    private double buildTreeMin(GameTreeNode rootNode, int plyDepth, double alpha, double beta){
        if(rootNode.isEndState() || plyDepth >= MAX_PLY_DEPTH){
            rootNode.evalBoardState();
            return rootNode.getEvalScore();
        }

        rootNode.generateChildren();
        List<GameTreeNode> nodes = rootNode.getChildren();

        for (GameTreeNode node: nodes) {
            GlobalCounter.increaseCounter();
            double nodeScore = buildTreeMax(node, plyDepth+1, alpha, beta);
            node.setEvalScore(nodeScore);
            if(nodeScore < beta){
                beta = nodeScore;
            }

            if(beta <= alpha){
                break;
            }
        }

        return beta;
    }
}
