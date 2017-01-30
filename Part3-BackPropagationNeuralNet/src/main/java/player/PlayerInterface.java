package player;

import game.BoardState;
import game.GameStateValue;

public interface PlayerInterface {
    PlayerMove getMove(BoardState boardState, GameStateValue playerStateValue, GameStateValue opponentStateValue);
}
