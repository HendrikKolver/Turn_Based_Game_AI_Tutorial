package player;

import game.BoardState;
import game.GameStateValue;
import neuralnetwork.NeuralNetwork;

import java.util.Scanner;

public class HumanPlayer implements PlayerInterface {

    @Override
    public PlayerMove getMove(BoardState boardState, GameStateValue playerStateValue, GameStateValue opponentStateValue) {
        boardState.printBoardState();

        Scanner reader = new Scanner(System.in);  // Reading from System.in


        System.out.println("please enter value row");
        int row = reader.nextInt();
        System.out.println("please enter value col");
        int col = reader.nextInt();

        return new PlayerMove(row, col);

    }
}
