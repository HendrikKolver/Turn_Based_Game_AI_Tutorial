package player;

import game.BoardState;
import game.GameStateValue;
import neuralnetwork.NeuralNetwork;

public interface PlayerInterface {
    PlayerMove getMove(BoardState boardState, GameStateValue playerStateValue, GameStateValue opponentStateValue, NeuralNetwork nn);
}
