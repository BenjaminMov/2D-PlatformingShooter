package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Pellet.PELLET_WIDTH;
import static model.Player.*;
import static model.World.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    Player testPlayer;

    @BeforeEach
    void runBefore(){
        testPlayer = new Player(P1_STARTX, P_STARTY);
    }


    @Test
    void testMove(){
        //initialization
        assertEquals(P_STARTY, testPlayer.getPlayerY());
        assertEquals(P1_STARTX, testPlayer.getPlayerX());
        //test moving in x direction
        testPlayer.setDx(5);
        testPlayer.move();
        assertEquals(P1_STARTX + 5, testPlayer.getPlayerX());
        //test moving in y direction
        testPlayer.jump();
        testPlayer.move();
        assertNotEquals(P_STARTY, testPlayer.getPlayerY());
        //test gravity
        testPlayer.setDy(0);
        testPlayer.setPlayerY(SCENE_HEIGHT / 2);
        testPlayer.setGravity(100);
        testPlayer.move();
        assertEquals(SCENE_HEIGHT / 2 + 100, testPlayer.getPlayerY());
        assertEquals(testPlayer.getDy(), 100);
        testPlayer.move();
        assertEquals(SCENE_HEIGHT / 2 + 100 + 200, testPlayer.getPlayerY());
        assertEquals(200, testPlayer.getDy());
        //test gravity off
        //- note this does not happen in game however it is required by autobot to ensure full coverage
        // the reason for this is that the code structure is the one I want to use later in development, but the
        // current conditions do not necessarily require to be checked
        testPlayer.setPlayerY(SCENE_HEIGHT);
        testPlayer.setDy(-2);
        testPlayer.setGravity(60);
        testPlayer.move();
        assertEquals(-2, testPlayer.getDy());
        assertEquals(SCENE_HEIGHT - 2, testPlayer.getPlayerY());
    }

    @Test
    void testLeftEnforceWall() {
        //within boundary
        testPlayer.enforceWall();
        assertEquals(P1_STARTX, testPlayer.getPlayerX());
        assertEquals(P_STARTY, testPlayer.getPlayerY());
        //outside left x boundary
        testPlayer.setPlayerX(-PLAYER_WIDTH - 100);
        testPlayer.enforceWall();
        assertEquals(PLAYER_WIDTH / 2, testPlayer.getPlayerX());
        //just outside left x boundary
        testPlayer.setPlayerX(PLAYER_WIDTH / 2 - 1);
        testPlayer.enforceWall();
        assertEquals(PLAYER_WIDTH / 2, testPlayer.getPlayerX());
        //on left x boundary
        testPlayer.setPlayerX(PLAYER_WIDTH / 2);
        testPlayer.enforceWall();
        assertEquals(PLAYER_WIDTH / 2, testPlayer.getPlayerX());
        //just within left x boundary
        testPlayer.setPlayerX(PLAYER_WIDTH / 2 + 1);
        testPlayer.enforceWall();
        assertEquals(PLAYER_WIDTH / 2 + 1, testPlayer.getPlayerX());
        //about to move out of left boundary
        testPlayer.setPlayerX(PLAYER_WIDTH / 2 + 1);
        testPlayer.setDx(-2);
        testPlayer.move();
        testPlayer.enforceWall();
        assertEquals(PLAYER_WIDTH / 2, testPlayer.getPlayerX());
        assertEquals(0, testPlayer.getDx());
    }

    @Test
    void testRightEnforceWall() {
        //outside right x boundary
        testPlayer.setPlayerX(SCENE_WIDTH + PLAYER_WIDTH / 2 + 100);
        testPlayer.enforceWall();
        assertEquals(SCENE_WIDTH - PLAYER_WIDTH / 2, testPlayer.getPlayerX());
        //just outside right x boundary
        testPlayer.setPlayerX(SCENE_WIDTH - PLAYER_WIDTH / 2 + 1);
        testPlayer.enforceWall();
        assertEquals(SCENE_WIDTH - PLAYER_WIDTH / 2, testPlayer.getPlayerX());
        //on right x boundary
        testPlayer.setPlayerX(SCENE_WIDTH - PLAYER_WIDTH / 2);
        testPlayer.enforceWall();
        assertEquals(SCENE_WIDTH - PLAYER_WIDTH / 2, testPlayer.getPlayerX());
        //just within right x boundary
        testPlayer.setPlayerX(SCENE_WIDTH - PLAYER_WIDTH / 2 - 1);
        testPlayer.enforceWall();
        assertEquals(SCENE_WIDTH - PLAYER_WIDTH / 2 - 1, testPlayer.getPlayerX());
        //about to move out of right boundary
        testPlayer.setPlayerX(SCENE_WIDTH - PLAYER_WIDTH / 2 - 1);
        testPlayer.setDx(2);
        testPlayer.move();
        testPlayer.enforceWall();
        assertEquals(SCENE_WIDTH - PLAYER_WIDTH / 2, testPlayer.getPlayerX());
        assertEquals(0, testPlayer.getDx());
    }

    @Test
    void testBottomEnforceWall() {
        //outside bottom boundary
        testPlayer.setPlayerY(SCENE_HEIGHT + PLAYER_HEIGHT / 2 + 100);
        testPlayer.enforceWall();
        assertEquals(SCENE_HEIGHT - PLAYER_HEIGHT / 2, testPlayer.getPlayerY());
        //just outside bottom boundary
        testPlayer.setPlayerY(SCENE_HEIGHT - PLAYER_HEIGHT / 2 + 1);
        testPlayer.enforceWall();
        assertEquals(SCENE_HEIGHT - PLAYER_HEIGHT / 2, testPlayer.getPlayerY());
        //on bottom boundary
        testPlayer.setPlayerY(SCENE_HEIGHT - PLAYER_HEIGHT / 2);
        testPlayer.enforceWall();
        assertEquals(SCENE_HEIGHT - PLAYER_HEIGHT / 2, testPlayer.getPlayerY());
        //just within bottom boundary
        testPlayer.setPlayerY(SCENE_HEIGHT - PLAYER_HEIGHT / 2 - 1);
        testPlayer.enforceWall();
        assertEquals(SCENE_HEIGHT - PLAYER_HEIGHT / 2 - 1, testPlayer.getPlayerY());
        //about to move out of bottom boundary
        testPlayer.setPlayerY(SCENE_HEIGHT - PLAYER_HEIGHT / 2 - 1);
        testPlayer.setDy(2);
        testPlayer.setGravity(0);
        testPlayer.move();
        testPlayer.enforceWall();
        assertEquals(SCENE_HEIGHT - PLAYER_HEIGHT / 2, testPlayer.getPlayerY());
        assertEquals(testPlayer.getDy(), 0);
    }

    @Test
    void testTopEnforceWall() {
        //outside top boundary
        testPlayer.setPlayerY(-PLAYER_HEIGHT - 100);
        testPlayer.enforceWall();
        assertEquals(PLAYER_HEIGHT / 2, testPlayer.getPlayerY());
        //just outside top boundary
        testPlayer.setPlayerY(PLAYER_HEIGHT / 2 - 1);
        testPlayer.enforceWall();
        assertEquals(PLAYER_HEIGHT / 2, testPlayer.getPlayerY());
        //on top boundary
        testPlayer.setPlayerY(PLAYER_HEIGHT / 2);
        testPlayer.enforceWall();
        assertEquals(PLAYER_HEIGHT / 2, testPlayer.getPlayerY());
        //just within top boundary
        testPlayer.setPlayerY(PLAYER_HEIGHT / 2 + 1);
        testPlayer.enforceWall();
        assertEquals(PLAYER_HEIGHT / 2 + 1, testPlayer.getPlayerY());
        //about to move out of left boundary
        testPlayer.setPlayerY(PLAYER_HEIGHT / 2 + 1);
        testPlayer.setDy(-2);
        testPlayer.setGravity(0);
        testPlayer.move();
        testPlayer.enforceWall();
        assertEquals(PLAYER_HEIGHT / 2, testPlayer.getPlayerY());
        assertEquals(0, testPlayer.getDy());
    }

    @Test
    void testReload() {
        assertEquals(testPlayer.getMagazine(), 0);
        testPlayer.reload();
        assertEquals(Player.RELOAD_AMOUNT, testPlayer.getMagazine());
        testPlayer.reload();
        assertEquals(RELOAD_AMOUNT * 2, testPlayer.getMagazine());
    }

    @Test
    void testPelletXStart() {
        testPlayer.setFacingRight(true);
        assertEquals(P1_STARTX + (PLAYER_WIDTH / 2) + PELLET_WIDTH / 2 + 1, testPlayer.pelletXStart());
        testPlayer.setFacingRight(false);
        assertEquals(P1_STARTX - (PLAYER_WIDTH / 2) - PELLET_WIDTH / 2 - 1, testPlayer.pelletXStart());
        testPlayer.setPlayerX(PLAYER_WIDTH);
        testPlayer.setFacingRight(true);
        assertEquals(PLAYER_WIDTH + (PLAYER_WIDTH / 2) + PELLET_WIDTH / 2 + 1, testPlayer.pelletXStart());
    }

    @Test
    void testShootDirection() {
        testPlayer.setFacingRight(true);
        assertEquals(1, testPlayer.shootDirection());
        testPlayer.setFacingRight(false);
        assertEquals(-1, testPlayer.shootDirection());
    }

    @Test
    void testShoot() {
        //insufficient ammo
        assertEquals(0, testPlayer.getMagazine());
        testPlayer.shoot();
        assertTrue(testPlayer.getPellets().isEmpty());
        // > 1 ammo
        testPlayer.reload();
        testPlayer.setFacingRight(true);
        testPlayer.shoot();
        assertFalse(testPlayer.getPellets().isEmpty());
        assertTrue(testPlayer.getFacingRight());
        assertEquals(testPlayer.pelletXStart(), testPlayer.getPellets().getElement(0).getPelletX());
        assertEquals(testPlayer.getPlayerY(), testPlayer.getPellets().getElement(0).getPelletY());
        assertEquals(Player.RELOAD_AMOUNT - 1, testPlayer.getMagazine());
        for (int i = 0; i < RELOAD_AMOUNT - 2; i++) {
            testPlayer.shoot();
        }
        assertEquals(1, testPlayer.getMagazine());
        assertEquals(RELOAD_AMOUNT - 1, testPlayer.getPellets().size());
        testPlayer.shoot();
        assertEquals(0, testPlayer.getMagazine());
        assertEquals(RELOAD_AMOUNT, testPlayer.getPellets().size());
        //no ammo again
        testPlayer.shoot();
        assertEquals(0, testPlayer.getMagazine());
        assertEquals(RELOAD_AMOUNT, testPlayer.getPellets().size());
    }

    @Test
    void testJump() {
        //in air
        testPlayer.setGravity(0);
        testPlayer.setPlayerY(SCENE_HEIGHT / 2);
        testPlayer.jump();
        testPlayer.move();
        assertEquals(SCENE_HEIGHT / 2,testPlayer.getPlayerY());
        testPlayer.setPlayerY(P_STARTY);
        testPlayer.setGravity(10);
        testPlayer.jump();
        //about to jump
        assertEquals(JUMP_STRENGTH, testPlayer.getDy());
        assertEquals(P_STARTY,testPlayer.getPlayerY());
        //off the ground
        testPlayer.move();
        assertEquals(JUMP_STRENGTH + 10, testPlayer.getDy());
        assertEquals(P_STARTY + JUMP_STRENGTH + 10, testPlayer.getPlayerY());
        //about to come back down
        testPlayer.move();
        assertEquals(JUMP_STRENGTH + 20, testPlayer.getDy());
        assertEquals(P_STARTY + JUMP_STRENGTH + 10 + JUMP_STRENGTH + 20, testPlayer.getPlayerY());
        //on ground
        testPlayer.move();
        assertEquals(JUMP_STRENGTH + 30, testPlayer.getDy());
        assertEquals(P_STARTY + JUMP_STRENGTH + 10 + JUMP_STRENGTH + 20 + JUMP_STRENGTH + 30,
                    testPlayer.getPlayerY());
        testPlayer.move();
        testPlayer.move();
        testPlayer.enforceWall();
        assertEquals(0, testPlayer.getDy());
    }

    @Test
    void testCheckIfGotShotBy() {
        Player testPlayer2 = new Player(P2_STARTX, P_STARTY);
        testPlayer.setFacingRight(true);
        testPlayer.reload();
        testPlayer.shoot();
        assertFalse(testPlayer2.checkifGotShotBy(testPlayer));
        //left edge of player
        testPlayer.getPellets().getElement(0).setPelletX(P2_STARTX - PLAYER_WIDTH / 2 - 1);
        assertFalse(testPlayer2.checkifGotShotBy(testPlayer));
        testPlayer.getPellets().getElement(0).setPelletX(P2_STARTX - PLAYER_WIDTH / 2);
        assertTrue(testPlayer2.checkifGotShotBy(testPlayer));
        testPlayer.getPellets().getElement(0).setPelletX(P2_STARTX - PLAYER_WIDTH / 2 + 1);
        assertTrue(testPlayer2.checkifGotShotBy(testPlayer));
        //right edge of player
        testPlayer.getPellets().getElement(0).setPelletX(P2_STARTX + PLAYER_WIDTH / 2 + 1);
        assertFalse(testPlayer2.checkifGotShotBy(testPlayer));
        testPlayer.getPellets().getElement(0).setPelletX(P2_STARTX + PLAYER_WIDTH / 2);
        assertTrue(testPlayer2.checkifGotShotBy(testPlayer));
        testPlayer.getPellets().getElement(0).setPelletX(P2_STARTX + PLAYER_WIDTH / 2 - 1);
        assertTrue(testPlayer2.checkifGotShotBy(testPlayer));
        //top of player
        testPlayer.getPellets().getElement(0).setPelletX(P2_STARTX);
        testPlayer.getPellets().getElement(0).setPelletY(P_STARTY - PLAYER_HEIGHT / 2 - 1);
        assertFalse(testPlayer2.checkifGotShotBy(testPlayer));
        testPlayer.getPellets().getElement(0).setPelletY(P_STARTY - PLAYER_HEIGHT / 2);
        assertTrue(testPlayer2.checkifGotShotBy(testPlayer));
        testPlayer.getPellets().getElement(0).setPelletY(P_STARTY - PLAYER_HEIGHT / 2 + 1);
        assertTrue(testPlayer2.checkifGotShotBy(testPlayer));
        //bottom of player
        testPlayer.getPellets().getElement(0).setPelletY(P_STARTY + PLAYER_HEIGHT / 2 + 1);
        assertFalse(testPlayer2.checkifGotShotBy(testPlayer));
        testPlayer.getPellets().getElement(0).setPelletY(P_STARTY + PLAYER_HEIGHT / 2);
        assertTrue(testPlayer2.checkifGotShotBy(testPlayer));
        testPlayer.getPellets().getElement(0).setPelletY(P_STARTY + PLAYER_HEIGHT / 2 - 1);
        assertTrue(testPlayer2.checkifGotShotBy(testPlayer));
        //middle of player
        testPlayer.getPellets().getElement(0).setPelletY(P_STARTY);
        assertTrue(testPlayer2.checkifGotShotBy(testPlayer));
        //within x but not y
        testPlayer.getPellets().getElement(0).setPelletY(0);
        assertFalse(testPlayer2.checkifGotShotBy(testPlayer));
        //within y but not x
        testPlayer.getPellets().getElement(0).setPelletY(P_STARTY);
        testPlayer.getPellets().getElement(0).setPelletX(0);
        assertFalse(testPlayer2.checkifGotShotBy(testPlayer));
    }
}
