import game.GameEngine;
import player.HumanPlayer;
import player.PlayerInterface;
import player.RandomMovePlayer;

public class Main {
    public static void main(String[] args) {
        PlayerInterface player1 = new HumanPlayer();
        PlayerInterface player2 = new RandomMovePlayer();
        GameEngine gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();

        if(gameEngine.getWinner() == player1){
            System.out.println("Player1 wins");
        }else if(gameEngine.getWinner() == player2){
            System.out.println("Player2 wins");
        }else{
            System.out.println("Draw");
        }

    }
}
