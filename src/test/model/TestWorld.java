package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Pellet.SHOT_SPEED;
import static org.junit.jupiter.api.Assertions.*;

public class TestWorld {

    World testGame;

    @BeforeEach
    void runBefore() {
        testGame = new World();
    }

    @Test
    void testUpdateNoMove() {
        //initialization
        assertEquals(World.P1_STARTX, testGame.getPlayer1().getPlayerX());
        assertEquals(World.P2_STARTX, testGame.getPlayer2().getPlayerX());
        assertTrue(testGame.getPlayer1().getPellets().isEmpty());
        assertTrue(testGame.getPlayer2().getPellets().isEmpty());
        //Players are stationary, no movement expected
        testGame.update();
        assertEquals(World.P1_STARTX, testGame.getPlayer1().getPlayerX());
        assertEquals(World.P2_STARTX, testGame.getPlayer2().getPlayerX());
        assertEquals(World.P_STARTY, testGame.getPlayer1().getPlayerY());
        assertEquals(World.P_STARTY, testGame.getPlayer2().getPlayerY());
        assertTrue(testGame.getPlayer1().getPellets().isEmpty());
        assertTrue(testGame.getPlayer2().getPellets().isEmpty());
    }

    @Test
    void testUpdateMoveNoCollision() {
        //change player dx and dy (without wall collision)
        testGame.getPlayer1().setDx(10);
        testGame.getPlayer2().setDx(-10);
        testGame.getPlayer1().setDy(-30);
        testGame.getPlayer2().setDy(-30);
        testGame.getPlayer1().setGravity(0);
        testGame.getPlayer2().setGravity(0);
        testGame.update();
        assertEquals(World.P1_STARTX + 10, testGame.getPlayer1().getPlayerX());
        assertEquals(World.P2_STARTX - 10, testGame.getPlayer2().getPlayerX());
        assertEquals(World.P_STARTY - 30, testGame.getPlayer1().getPlayerY());
        assertEquals(World.P_STARTY - 30, testGame.getPlayer2().getPlayerY());
    }

    @Test
    void testUpdateMoveAndCollision() {
        testGame.getPlayer1().setDx(30000);
        testGame.getPlayer2().setDy(-10000);
        testGame.update();
        assertEquals(World.SCENE_WIDTH - Player.PLAYER_WIDTH / 2.0, testGame.getPlayer1().getPlayerX());
        assertEquals(Player.PLAYER_HEIGHT / 2.0, testGame.getPlayer2().getPlayerY());
    }

    @Test
    void testUpdateMovingPellets() {
        testGame.getPlayer1().setFacingRight(true);
        testGame.getPlayer2().setFacingRight(false);
        testGame.getPlayer1().reload();
        testGame.getPlayer1().shoot();
        testGame.getPlayer2().reload();
        testGame.getPlayer2().shoot();
        testGame.update();
        assertEquals(World.P1_STARTX, testGame.getPlayer1().getPlayerX());
        assertEquals(World.P2_STARTX, testGame.getPlayer2().getPlayerX());
        assertEquals(World.P_STARTY, testGame.getPlayer1().getPlayerY());
        assertEquals(World.P_STARTY, testGame.getPlayer2().getPlayerY());
        //after 1 update
        assertEquals(World.P1_STARTX + Player.PLAYER_WIDTH / 2.0
                        + Pellet.PELLET_WIDTH / 2 + 1 + SHOT_SPEED,
                testGame.getPlayer1().getPellets().getElement(0).getPelletX());
        assertEquals(World.P2_STARTX - Player.PLAYER_WIDTH / 2.0
                        - Pellet.PELLET_WIDTH / 2 - 1 - SHOT_SPEED,
                testGame.getPlayer2().getPellets().getElement(0).getPelletX());
        //after 2 updates
        testGame.update();
        assertEquals(World.P1_STARTX + Player.PLAYER_WIDTH / 2.0
                        + Pellet.PELLET_WIDTH / 2 + 1 + 2 * SHOT_SPEED,
                testGame.getPlayer1().getPellets().getElement(0).getPelletX());
        assertEquals(World.P2_STARTX - Player.PLAYER_WIDTH / 2.0
                        - Pellet.PELLET_WIDTH / 2 - 1 - 2 * SHOT_SPEED,
                testGame.getPlayer2().getPellets().getElement(0).getPelletX());

    }

    @Test
    void testUpdateRemoveIfShot() {
        testGame.getPlayer1().setFacingRight(true);
        testGame.getPlayer2().setFacingRight(false);
        testGame.getPlayer2().setPlayerX(testGame.getPlayer1().pelletXStart() + 2 * SHOT_SPEED);
        testGame.getPlayer1().reload();
        testGame.getPlayer1().shoot();
        testGame.update();
        assertTrue(testGame.getPlayer2().getAlive());
        testGame.update();
        assertFalse(testGame.getPlayer2().getAlive());
        assertTrue(testGame.getIfGameOver());
    }
}
