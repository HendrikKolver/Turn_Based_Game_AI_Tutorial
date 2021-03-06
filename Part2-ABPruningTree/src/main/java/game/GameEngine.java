package game;

import player.PlayerInterface;
import player.PlayerMove;
import player.RandomMovePlayer;

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

    public GameEngine(BoardState boardState){
        this.boardState = boardState;
        playerNaughts = new RandomMovePlayer();
        playerCrosses = new RandomMovePlayer();
    }

    public void playGame(){
        while(!isGameFinished()){
            boolean wasMoveValid = false;
            while(!isGameFinished() && !wasMoveValid){
                PlayerMove move = playerNaughts.getMove(boardState, GameStateValue.NAUGHT, GameStateValue.CROSS);
                wasMoveValid = boardState.makeMove(GameStateValue.NAUGHT, move.row, move.col);
            }

            wasMoveValid = false;
            while(!isGameFinished() && !wasMoveValid){
                PlayerMove move = playerCrosses.getMove(boardState, GameStateValue.CROSS, GameStateValue.NAUGHT);
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
