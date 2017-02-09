package game;

import neuralnetwork.NeuralNetwork;
import player.PlayerInterface;
import player.PlayerMove;

//Noughts & Crosses
public class GameEngine {
    private final PlayerInterface playerNaughts;
    private final PlayerInterface playerCrosses;
    private BoardState boardState;

    public GameEngine(PlayerInterface playerNaughts, PlayerInterface playerCrosses){
        boardState = new BoardState();
        this.playerNaughts = playerNaughts;
        this.playerCrosses = playerCrosses;
    }

    public void playGame(NeuralNetwork neuralNetwork){
        while(!isGameFinished()){
            boolean wasMoveValid = false;
            while(!isGameFinished() && !wasMoveValid){
                PlayerMove move = playerNaughts.getMove(boardState, GameStateValue.NAUGHT, GameStateValue.CROSS, neuralNetwork);
                wasMoveValid = boardState.makeMove(GameStateValue.NAUGHT, move.row, move.col);
            }

            wasMoveValid = false;
            while(!isGameFinished() && !wasMoveValid){
                PlayerMove move = playerCrosses.getMove(boardState, GameStateValue.CROSS, GameStateValue.NAUGHT, neuralNetwork);
                wasMoveValid = boardState.makeMove(GameStateValue.CROSS, move.row, move.col);
            }
        }
    }

    public PlayerInterface getWinningPlayer() {
        GameStateValue result = GameEngineHelper.getWinner(boardState);
        if(result == GameStateValue.NAUGHT){
            return playerNaughts;
        }else if(result == GameStateValue.CROSS){
            return  playerCrosses;
        }

        return null;
    }



    private boolean isGameFinished(){
        return GameEngineHelper.isGameFinished(boardState);
    }

}
