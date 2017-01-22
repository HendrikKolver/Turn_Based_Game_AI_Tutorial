package gametree;

import game.BoardState;
import game.GameStateValue;
import player.PlayerMove;

import java.util.List;

public class MinMaxTree {

    private final BoardState boardState;
    //GameStateValue represents whether the player is X of O in a game of Naughts and Crosses
    private final GameStateValue playerState;
    private final GameStateValue opponentState;
    //Ply depth represents how many moves into the future the game tree should look. Using a value of fifty guarantees that an entire game of Naughts and Crosses will be calculated
    private final int MAX_PLY_DEPTH = 50;
    private final int MIN_CAP = -100;
    private final int MAX_CAP = 100;

    public MinMaxTree(GameStateValue player, GameStateValue opponent, BoardState boardState){
        this.boardState = boardState;
        this.playerState = player;
        this.opponentState = opponent;
    }

    public PlayerMove getOptimalMove(){
        //Create the root node of the game tree
        GameTreeNode rootNode = new GameTreeNode(opponentState, playerState, boardState);
        //build the game tree from the root node. buildTreeMax is called because the first move from the current state will always be the Player that we are trying to calculate the next move for
        buildTreeMax(rootNode, 0);
        List<GameTreeNode> children = rootNode.getChildren();


        //Get the child with the highest calculated game score from all the root node children
        GameTreeNode winningChild = null;
        double evalScoreMax = MIN_CAP;
        for (GameTreeNode child: children) {
            if(child.getEvalScore() > evalScoreMax){
                winningChild = child;
                evalScoreMax = child.getEvalScore();
            }
        }

        //Return the move that needs to be made to reach the winning state
        return winningChild.getMove();
    }

    private double buildTreeMax(GameTreeNode rootNode, int plyDepth){
        //Determine if the current position is a game end state or the max number of moves have been reached
        if(rootNode.isEndState() || plyDepth >= MAX_PLY_DEPTH){
            //If this is the case calculate the score for this game node and return it
            rootNode.evalBoardState();
            return rootNode.getEvalScore();
        }

        //An end state has not been reached so generate all possible moves that can be made from the current position
        rootNode.generateChildren();
        List<GameTreeNode> nodes = rootNode.getChildren();

        double evalScore = MIN_CAP;
        for (GameTreeNode node: nodes) {
            //for every possible move continue building the tree from the opponents(min's) perspective since the next move will be the opponents
            double nodeScore = buildTreeMin(node, plyDepth+1);
            //get the highest score that was propagated up in the game tree
            node.setEvalScore(nodeScore);
            if(nodeScore > evalScore){
                evalScore = nodeScore;
            }
        }

        //return the winning (Highest) score for this level in the tree
        return evalScore;
    }

    private double buildTreeMin(GameTreeNode rootNode, int plyDepth){
        //Determine if the current position is a game end state or the max number of moves have been reached
        if(rootNode.isEndState() || plyDepth >= MAX_PLY_DEPTH){
            //If this is the case calculate the score for this game node and return it
            rootNode.evalBoardState();
            return rootNode.getEvalScore();
        }

        //An end state has not been reached so generate all possible moves that can be made from the current position
        rootNode.generateChildren();
        List<GameTreeNode> nodes = rootNode.getChildren();

        double evalScore = MAX_CAP;
        for (GameTreeNode node: nodes) {
            //for every possible move continue building the tree from the AI player(max's) perspective since the next move will be the AI player
            double nodeScore = buildTreeMax(node, plyDepth+1);
            //get the lowest score (Because the opponent wants to minimise your chances of winning) that was propagated up in the game tree
            node.setEvalScore(nodeScore);
            if(nodeScore < evalScore){
                evalScore = nodeScore;
            }
        }
        //return the winning (Lowest) score for this level in the tree
        return evalScore;
    }
}
