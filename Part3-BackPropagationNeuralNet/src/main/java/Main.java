import game.GameEngine;
import neuralnetwork.NeuralNetwork;
import player.HumanPlayer;
import player.NeuralNetPlayer;
import player.PlayerInterface;

public class Main {
    public static void main(String[] args) {


        NeuralNetwork nn = new NeuralNetwork(4);
        nn.trainNetwork();

        PlayerInterface player1 = new NeuralNetPlayer();
        PlayerInterface player2 = new HumanPlayer();
        GameEngine gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame(nn);

        if(gameEngine.getWinningPlayer() == player1){
            System.out.println("Player1 wins");
        }else if(gameEngine.getWinningPlayer() == player2){
            System.out.println("Player2 wins");
        }else{
            System.out.println("Draw");
        }

    }
}
