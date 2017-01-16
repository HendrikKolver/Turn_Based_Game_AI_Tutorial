package player;

import game.BoardState;
import java.util.Scanner;

public class HumanPlayer implements PlayerInterface {

    @Override
    public PlayerMove getMove(BoardState boardState) {
        boardState.printBoardState();

        Scanner reader = new Scanner(System.in);  // Reading from System.in


        System.out.println("please enter value row");
        int row = reader.nextInt();
        System.out.println("please enter value col");
        int col = reader.nextInt();

        return new PlayerMove(row, col);

    }
}
