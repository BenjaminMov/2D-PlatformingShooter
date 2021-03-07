package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.World.*;
import static model.Pellet.SHOT_SPEED;
import static org.junit.jupiter.api.Assertions.*;

public class TestWorld {

    World testGame;
    Player testPlayer1;
    Player testPlayer2;

    @BeforeEach
    void runBefore() {
        testGame = new World();
        testGame.getLevel().addPlatform(new Platform(SCENE_WIDTH / 2.0, SCENE_HEIGHT / 2.0, 10));
        
        testPlayer1 = testGame.getPlayer1();
        testPlayer2 = testGame.getPlayer2();
    }

    @Test
    void testUpdateNoMove() {
        //initialization
        assertEquals(World.P1_STARTX, testPlayer1.getPlayerX());
        assertEquals(World.P2_STARTX, testPlayer2.getPlayerX());
        assertTrue(testPlayer1.getPellets().isEmpty());
        assertTrue(testPlayer2.getPellets().isEmpty());
        //Players are stationary, no movement expected
        testGame.update();
        assertEquals(World.P1_STARTX, testPlayer1.getPlayerX());
        assertEquals(World.P2_STARTX, testPlayer2.getPlayerX());
        assertEquals(World.P_STARTY, testPlayer1.getPlayerY());
        assertEquals(World.P_STARTY, testPlayer2.getPlayerY());
        assertTrue(testPlayer1.getPellets().isEmpty());
        assertTrue(testPlayer2.getPellets().isEmpty());
    }

    @Test
    void testUpdateMoveNoCollision() {
        //change player dx and dy (without wall collision)
        testPlayer1.setDx(10);
        testPlayer2.setDx(-10);
        testPlayer1.setDy(-30);
        testPlayer2.setDy(-30);
        testPlayer1.setGravity(0);
        testPlayer2.setGravity(0);
        testGame.update();
        assertEquals(World.P1_STARTX + 10, testPlayer1.getPlayerX());
        assertEquals(World.P2_STARTX - 10, testPlayer2.getPlayerX());
        assertEquals(World.P_STARTY - 30, testPlayer1.getPlayerY());
        assertEquals(World.P_STARTY - 30, testPlayer2.getPlayerY());
    }

    @Test
    void testUpdateMoveAndCollision() {
        testPlayer1.setDx(30000);
        testPlayer2.setDy(-10000);
        testGame.update();
        assertEquals(World.SCENE_WIDTH - Player.PLAYER_WIDTH / 2.0, testPlayer1.getPlayerX());
        assertEquals(Player.PLAYER_HEIGHT / 2.0, testPlayer2.getPlayerY());
    }

    @Test
    void testUpdateMovingPellets() {
        testPlayer1.setFacingRight(true);
        testPlayer2.setFacingRight(false);
        testPlayer1.reload();
        testPlayer1.shoot();
        testPlayer2.reload();
        testPlayer2.shoot();
        testGame.update();
        assertEquals(World.P1_STARTX, testPlayer1.getPlayerX());
        assertEquals(World.P2_STARTX, testPlayer2.getPlayerX());
        assertEquals(World.P_STARTY, testPlayer1.getPlayerY());
        assertEquals(World.P_STARTY, testPlayer2.getPlayerY());
        //after 1 update
        assertEquals(World.P1_STARTX + Player.PLAYER_WIDTH / 2.0
                        + Pellet.PELLET_WIDTH / 2 + 1 + SHOT_SPEED,
                testPlayer1.getPellets().getElement(0).getPelletX());
        assertEquals(World.P2_STARTX - Player.PLAYER_WIDTH / 2.0
                        - Pellet.PELLET_WIDTH / 2 - 1 - SHOT_SPEED,
                testPlayer2.getPellets().getElement(0).getPelletX());
        //after 2 updates
        testGame.update();
        assertEquals(World.P1_STARTX + Player.PLAYER_WIDTH / 2.0
                        + Pellet.PELLET_WIDTH / 2 + 1 + 2 * SHOT_SPEED,
                testPlayer1.getPellets().getElement(0).getPelletX());
        assertEquals(World.P2_STARTX - Player.PLAYER_WIDTH / 2.0
                        - Pellet.PELLET_WIDTH / 2 - 1 - 2 * SHOT_SPEED,
                testPlayer2.getPellets().getElement(0).getPelletX());

    }

    @Test
    void testUpdateRemoveIfShot() {
        testPlayer1.setFacingRight(true);
        testPlayer2.setFacingRight(false);
        testPlayer2.setPlayerX(testPlayer1.pelletXStart() + 2 * SHOT_SPEED);
        testPlayer1.reload();
        testPlayer1.shoot();
        testGame.update();
        assertTrue(testPlayer2.getAlive());
        testGame.update();
        assertFalse(testPlayer2.getAlive());
        assertTrue(testGame.getIfGameOver());
        testPlayer2.setAlive(true);
        testGame.setIfGameOver(false);
        assertFalse(testGame.getIfGameOver());
        testPlayer1.setAlive(false);
        testGame.update();
        assertTrue(testGame.getIfGameOver());
        testPlayer1.setAlive(true);
        testGame.setIfGameOver(false);
        testPlayer2.reload();
        testPlayer2.shoot();
        testGame.update();
        assertTrue(testPlayer1.getAlive());
        testGame.update();
        assertFalse(testPlayer1.getAlive());
    }

    @Test
    void testShootSelf() {
        testPlayer1.setFacingRight(true);
        testPlayer1.reload();
        testPlayer1.shoot();
        testPlayer1.setPlayerX(testPlayer1.pelletXStart() + 2 * SHOT_SPEED);
        testGame.update();
        assertTrue(testPlayer1.getAlive());
        testGame.update();
        assertFalse(testPlayer1.getAlive());
        assertTrue(testGame.getIfGameOver());
    }

    @Test
    void testUpdateGoingThroughPlatform() {
        testPlayer1.setGravity(0);
        testPlayer1.setDy(10);
        testPlayer1.setPlayerX(SCENE_WIDTH / 2.0);
        testPlayer1.setPlayerY(SCENE_HEIGHT / 2.0 - Player.PLAYER_HEIGHT);
        testGame.update();
        assertEquals(testGame.getLevel().getPlatform(0).getPlatformY()
                        - Player.PLAYER_HEIGHT / 2.0 - Platform.PLATFORM_HEIGHT,
                testPlayer1.getPlayerY());
        assertEquals(0, testPlayer1.getDy());
    }

    @Test
    void testUpdateUnderPlatform() {
        testPlayer1.setGravity(0);
        testPlayer1.setDy(10);
        testPlayer1.setPlayerX(SCENE_WIDTH / 2.0);
        testPlayer1.setPlayerY(SCENE_HEIGHT / 2.0);
        testGame.update();
        assertEquals(SCENE_HEIGHT / 2.0 + 10, testPlayer1.getPlayerY());
    }


    @Test
    void testUpdateAbovePlatform() {
        testPlayer1.setGravity(0);
        testPlayer1.setDy(0);
        testPlayer1.setPlayerX(SCENE_WIDTH / 2.0);
        testPlayer1.setPlayerY(SCENE_HEIGHT / 2.0 - Player.PLAYER_HEIGHT);
        testGame.update();
        assertEquals(SCENE_HEIGHT / 2.0 - Player.PLAYER_HEIGHT, testPlayer1.getPlayerY());
    }

    @Test
    void testUpdateOnPlatform() {
        testPlayer1.setGravity(0);
        testPlayer1.setDy(0);
        testPlayer1.setPlayerX(SCENE_WIDTH / 2.0);
        testPlayer1.setPlayerY(SCENE_HEIGHT / 2.0 - Player.PLAYER_HEIGHT / 2.0 - Platform.PLATFORM_HEIGHT);
        testGame.update();
        assertEquals(testPlayer1.getPlayerY(),
                SCENE_HEIGHT / 2.0 - Player.PLAYER_HEIGHT / 2.0 - Platform.PLATFORM_HEIGHT);
    }

    @Test
    void testUpdateGoingThroughPlatformWithGravity() {
        testPlayer1.setGravity(30);
        testPlayer1.setDy(10);
        testPlayer1.setPlayerX(SCENE_WIDTH / 2.0);
        testPlayer1.setPlayerY(SCENE_HEIGHT / 2.0 - 100);
        testGame.update();
        assertEquals(SCENE_HEIGHT / 2.0 - 100 + 40, testPlayer1.getPlayerY());
        testGame.update();
        assertEquals(testGame.getLevel().getPlatform(0).getPlatformY()
                - Player.PLAYER_HEIGHT / 2.0 - Platform.PLATFORM_HEIGHT,
                testPlayer1.getPlayerY());

    }
    
    @Test
    void testPlayerOnLeftEdgeOfPlatform() {
        testPlayer1.setGravity(0);
        double startY = SCENE_HEIGHT / 2.0 - Player.PLAYER_HEIGHT / 2.0 - 2;
        double platformX = testGame.getLevel().getPlatform(0).getPlatformX();
        double platformWidth = testGame.getLevel().getPlatform(0).getPlatformWidth();
        testPlayer1.setDy(10);
        testPlayer1.setPlayerY(startY);
        testPlayer1.setPlayerX(platformX - platformWidth / 2);
        testGame.update();
        assertEquals(startY, testPlayer1.getPlayerY());
        testPlayer1.setDy(10);
        testPlayer1.setPlayerX(platformX - platformWidth / 2 - 1);
        testGame.update();
        assertEquals(startY ,testPlayer1.getPlayerY());
        testPlayer1.setDy(10);
        testPlayer1.setPlayerX(platformX - Player.PLAYER_WIDTH / 2.0 - platformWidth / 2);
        testGame.update();
        assertEquals(startY ,testPlayer1.getPlayerY());
        testPlayer1.setDy(10);
        testPlayer1.setPlayerX(platformX - Player.PLAYER_WIDTH / 2.0 - platformWidth / 2 - 1);
        testGame.update();
        assertEquals(startY + 10,testPlayer1.getPlayerY());
    }

    @Test
    void testPlayerOnRightEdgeOfPlatform() {
        testPlayer1.setGravity(0);
        double startY = SCENE_HEIGHT / 2.0 - Player.PLAYER_HEIGHT / 2.0 - 2;
        double platformX = testGame.getLevel().getPlatform(0).getPlatformX();
        double platformWidth = testGame.getLevel().getPlatform(0).getPlatformWidth();
        testPlayer1.setDy(10);
        testPlayer1.setPlayerY(startY);
        testPlayer1.setPlayerX(platformX + platformWidth / 2);
        testGame.update();
        assertEquals(startY, testPlayer1.getPlayerY());
        testPlayer1.setDy(10);
        testPlayer1.setPlayerX(platformX + platformWidth / 2 + 1);
        testGame.update();
        assertEquals(startY ,testPlayer1.getPlayerY());
        testPlayer1.setDy(10);
        testPlayer1.setPlayerX(platformX + Player.PLAYER_WIDTH / 2.0 + platformWidth / 2);
        testGame.update();
        assertEquals(startY ,testPlayer1.getPlayerY());
        testPlayer1.setDy(10);
        testPlayer1.setPlayerX(platformX + Player.PLAYER_WIDTH / 2.0 + platformWidth / 2 + 1);
        testGame.update();
        assertEquals(startY + 10,testPlayer1.getPlayerY());
    }

    @Test
    void testJumpOnPlatform() {
        testPlayer1.setPlayerX(SCENE_WIDTH / 2.0);
        testPlayer1.setPlayerY(SCENE_HEIGHT / 2.0 - Player.PLAYER_HEIGHT / 2.0 - 2);
        testPlayer1.setDy(0);
        testGame.update();
        assertEquals(SCENE_HEIGHT / 2.0 - Player.PLAYER_HEIGHT / 2.0 - 2, testPlayer1.getPlayerY());
        testPlayer1.setDy(40);
        testGame.update();
        assertEquals(SCENE_HEIGHT / 2.0 - Player.PLAYER_HEIGHT / 2.0 - 2, testPlayer1.getPlayerY());
        assertEquals(0, testPlayer1.getDy());
        testPlayer1.jump();
        assertEquals(Player.JUMP_STRENGTH, testPlayer1.getDy());

    }

    @Test
    void testGoingUpThroughPlatform() {
        testPlayer1.setPlayerX(SCENE_WIDTH / 2.0);
        testPlayer1.setPlayerY(SCENE_HEIGHT / 2.0 - Platform.PLATFORM_HEIGHT - Player.PLAYER_HEIGHT / 2.0);
        testPlayer1.setDy(-20);
        testGame.update();
        assertEquals(SCENE_HEIGHT / 2.0 - Platform.PLATFORM_HEIGHT - Player.PLAYER_HEIGHT / 2.0 - 20,
                     testPlayer1.getPlayerY());
    }

    @Test
    void testSetLevel() {
        Level testLevel1 = new Level("name1");
        Level testLevel2 = new Level("name2");
        testGame.setLevel(testLevel1);
        assertEquals(testGame.getLevel(), testLevel1);
        testGame.setLevel(testLevel2);
        assertEquals(testGame.getLevel(), testLevel2);
    }

}
