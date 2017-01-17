package game;

public class GameEngineHelper {

    public static boolean isGameFinished(BoardState state){
        if(getWinner(state) != null){
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

    public static GameStateValue getWinner(BoardState boardState){
        GameStateValue value = getResultForRow(0,0, 0,1, 0,2, boardState);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return value;
        }

        value = getResultForRow(1,0, 1,1, 1,2, boardState);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return value;
        }

        value = getResultForRow(2,0, 2,1, 2,2, boardState);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return value;
        }

        value = getResultForRow(0,0, 1,0, 2,0, boardState);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return value;
        }

        value = getResultForRow(0,1, 1,1, 2,1, boardState);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return value;
        }

        value = getResultForRow(0,2, 1,2, 2,2, boardState);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return value;
        }

        value = getResultForRow(0,0, 1,1, 2,2, boardState);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return value;
        }

        value = getResultForRow(2,0, 1,1, 0,2, boardState);
        if(value == GameStateValue.NAUGHT || value == GameStateValue.CROSS){
            return value;
        }

        return null;
    }

    private static GameStateValue getResultForRow(int row1, int col1, int row2, int col2, int row3, int col3, BoardState boardState) {
        if(boardState.getValue(row1,col1).equals(boardState.getValue(row2,col2)) && boardState.getValue(row1,col1).equals(boardState.getValue(row3,col3))){
            return boardState.getValue(row1,col1);
        }
        return null;
    }
}
