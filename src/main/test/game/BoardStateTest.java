package game;

import org.junit.Assert;
import org.junit.Test;

public class BoardStateTest {

    @Test
    public void shouldMakeMoveIfPositionIsEmpty() throws Exception {
        BoardState boardState = new BoardState();
        boolean value = boardState.makeMove(GameStateValue.NAUGHT, 0, 0);
        Assert.assertTrue(value);
        Assert.assertEquals(boardState.getValue(0,0), GameStateValue.NAUGHT);
    }

    @Test
    public void shouldNotMakeMoveIfPositionIsNotEmpty() throws Exception {
        BoardState boardState = new BoardState();
        boardState.makeMove(GameStateValue.NAUGHT, 0, 0);
        boolean value = boardState.makeMove(GameStateValue.CROSS, 0, 0);
        Assert.assertFalse(value);
        Assert.assertEquals(boardState.getValue(0,0), GameStateValue.NAUGHT);
    }

    @Test
    public void shouldBeEmpty() throws Exception {
        BoardState boardState = new BoardState();
        Assert.assertTrue(boardState.isEmpty(0,0));
    }

    @Test
    public void shouldNotBeEmpty() throws Exception {
        BoardState boardState = new BoardState();
        boardState.makeMove(GameStateValue.NAUGHT, 0, 0);
        Assert.assertFalse(boardState.isEmpty(0,0));
    }

}