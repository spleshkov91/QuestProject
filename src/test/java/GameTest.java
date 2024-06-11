import com.pleshkov.Game;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {
    private Game game;
    @Before
    public void setUp() throws IOException {
        game = new Game();
    }
    @Test
    public void startGameTest() {
        String message = game.startGame();
        assertEquals("Вы потеряли память. Принять вызов НЛО?", message);
    }
    @Test
    public void acceptUFOCallTest() {
        game.startGame();
        String result = game.processAnswer("Принять вызов НЛО");
        assertEquals("Вы приняли вызов НЛО. Вы собираетесь подняться к капитану?", result);
    }
    @Test
    public void climbToCaptainTest() {
        game.startGame();
        game.processAnswer("Принять вызов НЛО");
        String result = game.processAnswer("Подняться к капитану");
        assertEquals("Вы поднялись к капитану. Кто Вы?", result);
    }
    @Test
    public void tellTruthTest() {
        game.startGame();
        game.processAnswer("Принять вызов НЛО");
        game.processAnswer("Подняться к капитану");
        String result = game.processAnswer("Рассказать правду о себе");
        assertEquals("Вас вернули домой!", result);
        assertTrue(game.isVictory());
    }
    @Test
    public void declineUFOCallTest() {
        game.startGame();
        String result = game.processAnswer("Отклонить вызов НЛО");
        assertEquals("Вы отклонили вызов НЛО..", result);
        assertTrue(game.isDefeated());
    }
    @Test
    public void refuseCaptainTest() {
        game.startGame();
        game.processAnswer("Принять вызов НЛО");
        String result = game.processAnswer("Отказаться подниматься");
        assertEquals("Вы отказались подняться к капитану...", result);
        assertTrue(game.isDefeated());
    }
    @Test
    public void tellLieTest() {
        game.startGame();
        game.processAnswer("Принять вызов НЛО");
        game.processAnswer("Подняться к капитану");
        String result = game.processAnswer("Солгать о себе");
        assertEquals("Ваша ложь раскрыта...", result);
        assertTrue(game.isDefeated());
    }
    @Test
    public void testResetGame() {
        game.startGame();
        game.processAnswer("Отклонить вызов НЛО");
        game.resetGame();
        assertFalse(game.isDefeated());
        assertFalse(game.isVictory());
        assertEquals("Вы потеряли память. Принять вызов НЛО?", game.startGame());
    }
}