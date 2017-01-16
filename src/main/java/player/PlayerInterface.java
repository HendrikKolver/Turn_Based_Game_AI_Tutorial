package player;

import game.BoardState;

public interface PlayerInterface {
    PlayerMove getMove(BoardState boardState);
}
