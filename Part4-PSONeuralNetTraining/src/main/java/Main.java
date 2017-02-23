import game.GameEngine;
import player.HumanPlayer;
import player.NeuralNetPlayer;
import player.PlayerInterface;
import PSO.PSO;
import player.RandomPlayer;

public class Main {
    public static void main(String[] args) {

        PSO pso = new PSO();
        NeuralNetPlayer bestPlayer = pso.trainPlayer();

        PlayerInterface player1 = new NeuralNetPlayer(bestPlayer.getPersonalBestNetwork());
        PlayerInterface player2 = new RandomPlayer();

        double player1Count = 0;
        double player2Count = 0;
        int drawCount = 0;

        for (int i = 0; i < 200; i++) {
            GameEngine gameEngine = new GameEngine(player1, player2);
            gameEngine.playGame();
            if (gameEngine.getWinningPlayer() == player1) {
                player1Count++;
            } else if (gameEngine.getWinningPlayer() == player2) {
                player2Count++;

            } else {
                drawCount++;
            }

            gameEngine = new GameEngine(player2, player1);
            gameEngine.playGame();
            if (gameEngine.getWinningPlayer() == player1) {
                player1Count++;
            } else if (gameEngine.getWinningPlayer() == player2) {
                player2Count++;

            } else {
                drawCount++;
            }
        }

        System.out.println("Player 1 count: "+ player1Count);
        System.out.println("Player 2 count: "+ player2Count);

        System.out.println("Player 1 win percentage: "+ Math.round((player1Count/(player1Count+player2Count))*100));
        System.out.println("Player 2 win percentage: "+ Math.round((player2Count/(player1Count+player2Count))*100));


    }
}
