import game.GameEngine;
import player.HumanPlayer;
import player.NeuralNetPlayer;
import player.PlayerInterface;
import PSO.PSO;

public class Main {
    public static void main(String[] args) {

        PSO pso = new PSO();
        NeuralNetPlayer bestPlayer = pso.trainPlayer();

        PlayerInterface player1 = new NeuralNetPlayer(bestPlayer.getPersonalBestNetwork());
        PlayerInterface player2 = new HumanPlayer();
        GameEngine gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();

        if(gameEngine.getWinningPlayer() == player1){
            System.out.println("Player1 wins");
        }else if(gameEngine.getWinningPlayer() == player2){
            System.out.println("Player2 wins");
        }else{
            System.out.println("Draw");
        }

    }
}
