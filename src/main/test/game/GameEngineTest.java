package game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import player.PlayerInterface;
import player.PlayerMove;


public class GameEngineTest {
    private PlayerInterface player1;
    private PlayerInterface player2;
    private GameEngine gameEngine;

    @Test
    public void testGameEngineTopRow() throws Exception {
        player1 = new topRowPlayer();
        player2 = new secondRowPlayer();
        gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();
        PlayerInterface winningPlayer = gameEngine.getWinner();
        Assert.assertTrue(gameEngine.isGameFinished());
        Assert.assertEquals(winningPlayer, player1);
    }

    @Test
    public void testGameEngineSecondRow() throws Exception {
        player1 = new secondRowPlayer();
        player2 = new topRowPlayer();
        gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();
        PlayerInterface winningPlayer = gameEngine.getWinner();
        Assert.assertTrue(gameEngine.isGameFinished());
        Assert.assertEquals(winningPlayer, player1);
    }

    @Test
    public void testGameEngineThirdRow() throws Exception {
        player1 = new thirdRowPlayer();
        player2 = new topRowPlayer();
        gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();
        PlayerInterface winningPlayer = gameEngine.getWinner();
        Assert.assertTrue(gameEngine.isGameFinished());
        Assert.assertEquals(winningPlayer, player1);
    }

    @Test
    public void testGameEngineFirstCol() throws Exception {
        player1 = new firstColPlayer();
        player2 = new secondColPlayer();
        gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();
        PlayerInterface winningPlayer = gameEngine.getWinner();
        Assert.assertTrue(gameEngine.isGameFinished());
        Assert.assertEquals(winningPlayer, player1);
    }

    @Test
    public void testGameEngineSecondCol() throws Exception {
        player1 = new secondColPlayer();
        player2 = new firstColPlayer();
        gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();
        PlayerInterface winningPlayer = gameEngine.getWinner();
        Assert.assertTrue(gameEngine.isGameFinished());
        Assert.assertEquals(winningPlayer, player1);
    }

    @Test
    public void testGameEngineThirdCol() throws Exception {
        player1 = new thirdColPlayer();
        player2 = new firstColPlayer();
        gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();
        PlayerInterface winningPlayer = gameEngine.getWinner();
        Assert.assertTrue(gameEngine.isGameFinished());
        Assert.assertEquals(winningPlayer, player1);
    }

    @Test
    public void testGameEngineDiagonal1() throws Exception {
        player1 = new increasingCountPlayer();
        player2 = new increasingCountPlayer();
        gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();
        PlayerInterface winningPlayer = gameEngine.getWinner();
        Assert.assertTrue(gameEngine.isGameFinished());
        Assert.assertEquals(winningPlayer, player1);
    }

    @Test
    public void testGameEngineDiagonal2() throws Exception {
        player1 = new cornerPlayer();
        player2 = new diagonalPlayer();
        gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();
        PlayerInterface winningPlayer = gameEngine.getWinner();
        Assert.assertTrue(gameEngine.isGameFinished());
        Assert.assertEquals(winningPlayer, player2);
    }

    @Test
    public void testGameEngineDraw() throws Exception {
        player1 = new drawPlayer1();
        player2 = new drawPlayer2();
        gameEngine = new GameEngine(player1, player2);
        gameEngine.playGame();
        PlayerInterface winningPlayer = gameEngine.getWinner();
        Assert.assertTrue(gameEngine.isGameFinished());
        Assert.assertEquals(winningPlayer, null);
    }

    private class increasingCountPlayer implements PlayerInterface{
        private int row = 0;
        private int col = 0;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            PlayerMove move = new PlayerMove(row, col);

            col++;
            if(col >= 3){
                row++;
                col = 0;
            }

            return move;
        }
    }

    private class topRowPlayer implements PlayerInterface{
        private int row = 0;
        private int col = 0;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            PlayerMove move = new PlayerMove(row, col);

            col++;

            return move;
        }
    }

    private class secondRowPlayer implements PlayerInterface{
        private int row = 1;
        private int col = 0;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            PlayerMove move = new PlayerMove(row, col);

            col++;

            return move;
        }
    }

    private class thirdRowPlayer implements PlayerInterface{
        private int row = 2;
        private int col = 0;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            PlayerMove move = new PlayerMove(row, col);

            col++;

            return move;
        }
    }

    private class firstColPlayer implements PlayerInterface{
        private int row = 0;
        private int col = 0;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            PlayerMove move = new PlayerMove(row, col);

            row++;

            return move;
        }
    }

    private class secondColPlayer implements PlayerInterface{
        private int row = 0;
        private int col = 1;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            PlayerMove move = new PlayerMove(row, col);

            row++;

            return move;
        }
    }

    private class thirdColPlayer implements PlayerInterface{
        private int row = 0;
        private int col = 2;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            PlayerMove move = new PlayerMove(row, col);

            row++;

            return move;
        }
    }

    private class diagonalPlayer implements PlayerInterface{
        private int row = 0;
        private int col = 0;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            PlayerMove move = new PlayerMove(row, col);
            row++;
            col++;
            return move;
        }
    }


    private class cornerPlayer implements PlayerInterface{
        private int state = 0;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            if(state == 0){
                state++;
                return new PlayerMove(0, 2);
            }
            if(state == 1){
                state++;
                return new PlayerMove(0, 1);
            }

            return new PlayerMove(1, 2);

        }
    }

    private class drawPlayer1 implements PlayerInterface{
        private int state = 0;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            if(state == 0){
                state++;
                return new PlayerMove(0, 0);
            }
            if(state == 1){
                state++;
                return new PlayerMove(0, 2);
            }
            if(state == 2){
                state++;
                return new PlayerMove(1, 0);
            }
            if(state == 3){
                state++;
                return new PlayerMove(2,1);
            }

            return new PlayerMove(2, 2);

        }
    }

    private class drawPlayer2 implements PlayerInterface{
        private int state = 0;

        @Override
        public PlayerMove getMove(BoardState boardState) {
            if(state == 0){
                state++;
                return new PlayerMove(0, 1);
            }
            if(state == 1){
                state++;
                return new PlayerMove(1, 1);
            }
            if(state == 2){
                state++;
                return new PlayerMove(1, 2);
            }
            if(state == 3){
                state++;
                return new PlayerMove(2,0);
            }

            return new PlayerMove(2, 2);

        }
    }


}