package PSO;


import game.GameEngine;
import player.NeuralNetPlayer;

import java.util.List;

public class Tournament {

    public static void runTournament(List<NeuralNetPlayer> players) {
        for (NeuralNetPlayer player : players) {
            for (NeuralNetPlayer opponent : players) {
                if (player == opponent) {
                    //don't play against yourself as this uses unnecessary processing time
                    break;
                }

                //Play a game agains each other potential solution as both naughts and crosses and tally each players total win and loss score

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
        //only consider wins and losses. You could also potentially include a slightly negative score for a tie but in naughts and crosses
        //this does not drastically improve the final solution
        if (gameEngine.getWinningPlayer() == player) {
            player.wonGame();
        } else if (gameEngine.getWinningPlayer() != null) {
            player.lostGame();
        }
    }
}
