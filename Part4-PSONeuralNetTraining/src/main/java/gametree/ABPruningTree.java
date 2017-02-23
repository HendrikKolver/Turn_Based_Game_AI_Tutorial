package gametree;

import game.BoardState;
import game.GameStateValue;
import neuralnetwork.NeuralNetwork;
import player.PlayerMove;

import java.util.List;

public class ABPruningTree {

    private final BoardState boardState;
    private final GameStateValue playerState;
    private final GameStateValue opponentState;
    private final int MAX_PLY_DEPTH = 4;
    private final int MIN_CAP = -100;
    private final int MAX_CAP = 100;
    private final NeuralNetwork neuralNetwork;

    public ABPruningTree(GameStateValue player, GameStateValue opponent, BoardState boardState, NeuralNetwork nn){
        this.boardState = boardState;
        this.playerState = player;
        this.opponentState = opponent;
        this.neuralNetwork = nn;
    }

    public PlayerMove getOptimalMove(){
        GameTreeNode rootNode = new GameTreeNode(opponentState, playerState, boardState, neuralNetwork);
        //We now pass initial values to the build tree function which represent the initial values of alpha and beta
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
            //Pass the current values of alpha and beta to the next level of the tree
            double nodeScore = buildTreeMin(node, plyDepth+1, alpha, beta);
            node.setEvalScore(nodeScore);
            //alpha represents the max so if the current node's score is larger then update alpha
            if(nodeScore > alpha){
                alpha = nodeScore;
            }

            //if beta is less or equal to alpha then that means that in the best case scenario a child node with a score larger than or equal one already available will be chosen so no other children are calculated
            //this is because this levels parent node is on a min level so it will choose its child with the smallest score
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
            //Pass the current values of alpha and beta to the next level of the tree
            double nodeScore = buildTreeMax(node, plyDepth+1, alpha, beta);
            node.setEvalScore(nodeScore);
            //beta represents the min so if the nodeScore is less update beta
            if(nodeScore < beta){
                beta = nodeScore;
            }

            //if beta is less or equal to alpha then that means that in the best case scenario a child node with a score less than or equal one already available will be chosen so no other children are calculated
            //this is because this levels parent node is on a max level so it will choose its child with the largest score
            if(beta <= alpha){
                break;
            }
        }

        return beta;
    }
}
