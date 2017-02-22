package player;

import game.BoardState;
import game.GameStateValue;

import java.util.Random;
import java.util.Scanner;

public class RandomPlayer implements PlayerInterface {
    private final Random random = new Random();

    @Override
    public PlayerMove getMove(BoardState boardState, GameStateValue playerStateValue, GameStateValue opponentStateValue) {
        return new PlayerMove(random.nextInt(3), random.nextInt(3));
    }
}
