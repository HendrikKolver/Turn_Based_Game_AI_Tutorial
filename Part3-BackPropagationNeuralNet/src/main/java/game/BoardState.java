package game;

import java.util.ArrayList;
import java.util.List;

public class BoardState {
    private GameStateValue[][] gameBoard;

    public BoardState(){
        gameBoard = new GameStateValue[3][3];
        initGameBoard();
    }

    public BoardState(GameStateValue[][] gameBoard){
        this.gameBoard = gameBoard;

    }

    private void initGameBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoard[row][col]= GameStateValue.EMPTY;
            }
        }
    }



    //returns true if move is valid
    public boolean makeMove(GameStateValue move, int positionRow, int positionCol){
        if(isEmpty(positionRow, positionCol)){
            gameBoard[positionRow][positionCol] = move;
            return true;
        }
        return false;
    }

    public boolean isEmpty(int positionRow, int positionCol) {
        return (gameBoard[positionRow][positionCol] == GameStateValue.EMPTY);
    }

    public GameStateValue getValue(int row, int col) {
        return gameBoard[row][col];
    }

    @Override
    public BoardState clone(){
        GameStateValue[][] clonedGameBoard = new GameStateValue[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                clonedGameBoard[row][col]= gameBoard[row][col];
            }
        }
        BoardState clonedState = new BoardState(clonedGameBoard);

        return  clonedState;
    }

    public List<Double> getNumericBoardValue(GameStateValue player)
    {
        List<Double>  boardValue = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if(gameBoard[row][col] == player){
                    boardValue.add(1.0);
                }else if(gameBoard[row][col] == GameStateValue.EMPTY){
                    boardValue.add(0.0);
                }else{
                    boardValue.add(2.0);
                }
            }
        }
        return boardValue;
    }
    public void printBoardState() {
        for (int row = 0; row < 3; row++) {
            String rowValue = "|";
            for (int col = 0; col < 3; col++) {
               if(gameBoard[row][col] == GameStateValue.NAUGHT){
                    rowValue+= " 0 |";
               }else if(gameBoard[row][col] == GameStateValue.CROSS){
                   rowValue+= " X |";
               }else{
                   rowValue+= "   |";
               }
            }
            System.out.println(rowValue);
        }
    }
}
