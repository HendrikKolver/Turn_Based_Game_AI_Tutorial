package player;


import game.BoardState;

import java.util.concurrent.ThreadLocalRandom;

public class RandomMovePlayer implements PlayerInterface {

    @Override
    public PlayerMove getMove(BoardState boardState) {
        return new PlayerMove(ThreadLocalRandom.current().nextInt(0, 3), ThreadLocalRandom.current().nextInt(0, 3));
    }
}
