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

    //Returns winning main.java.player or null for a draw
    public PlayerInterface getWinner(){
        GameStateValue value = getResultForRow(0,0, 0,1, 0,2);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return getPlayer(value);
        }

        value = getResultForRow(1,0, 1,1, 1,2);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return getPlayer(value);
        }

         value = getResultForRow(2,0, 2,1, 2,2);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return getPlayer(value);
        }

         value = getResultForRow(0,0, 1,0, 2,0);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return getPlayer(value);
        }

         value = getResultForRow(0,1, 1,1, 2,1);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return getPlayer(value);
        }

         value = getResultForRow(0,2, 1,2, 2,2);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return getPlayer(value);
        }

         value = getResultForRow(0,0, 1,1, 2,2);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return getPlayer(value);
        }

         value = getResultForRow(2,0, 1,1, 0,2);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return getPlayer(value);
        }

        return null;
    }

    private PlayerInterface getPlayer(GameStateValue resultForRow) {
        if(resultForRow == GameStateValue.NAUGHT){
            return playerNaughts;
        }
        return  playerCrosses;
    }

    private GameStateValue getResultForRow(int row1, int col1, int row2, int col2, int row3, int col3) {
        if(boardState.getValue(row1,col1).equals(boardState.getValue(row2,col2)) && boardState.getValue(row1,col1).equals(boardState.getValue(row3,col3))){
           return boardState.getValue(row1,col1);
        }
        return null;
    }

    public boolean isGameFinished(){
        return isGameFinished(boardState);
    }

    public boolean isGameFinished(BoardState state){
        if(getWinner() != null){
            return true;
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if(state.getValue(row, col) == GameStateValue.EMPTY){
                    return false;
                }
            }
        }

        return true;
    }


    public GameStateValue getWinningState() {
        PlayerInterface winningPlayer = getWinner();
        if(winningPlayer == playerNaughts){
            return GameStateValue.NAUGHT;
        }else if(winningPlayer == playerCrosses){
            return GameStateValue.CROSS;
        }

        return GameStateValue.EMPTY;


    }
}
