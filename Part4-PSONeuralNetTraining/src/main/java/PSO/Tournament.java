package PSO;


import game.GameEngine;
import player.NeuralNetPlayer;

import java.util.List;

public class Tournament {

    public static void runTournament(List<NeuralNetPlayer> players) {


        for (NeuralNetPlayer player : players) {
            for (NeuralNetPlayer opponent : players) {
                if (player == opponent) {
                    //dont play against yourself
                    break;
                }

                //Play a game as player Naughts
                GameEngine gameEngine = new GameEngine(player, opponent);
                gameEngine.playGame();
                updatePlayerScore(gameEngine, player);


                //Play a game as player Crosses
                gameEngine = new GameEngine(opponent, player);
                gameEngine.playGame();
                updatePlayerScore(gameEngine, player);
            }
        }
    }

    private static void updatePlayerScore(GameEngine gameEngine, NeuralNetPlayer player) {
        if (gameEngine.getWinningPlayer() == player) {
            player.wonGame();
        } else if (gameEngine.getWinningPlayer() != null) {
            player.lostGame();
        }
    }
}
