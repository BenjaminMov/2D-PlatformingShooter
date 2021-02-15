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
        testPlayer.setPlayerY(SCENE_HEIGHT / 2.0);
        testPlayer.setGravity(100);
        testPlayer.move();
        assertEquals(SCENE_HEIGHT / 2.0 + 100, testPlayer.getPlayerY());
        assertEquals(testPlayer.getDy(), 100);
        testPlayer.move();
        assertEquals(SCENE_HEIGHT / 2.0 + 100 + 200, testPlayer.getPlayerY());
        assertEquals(200, testPlayer.getDy());
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
        assertEquals(PLAYER_WIDTH / 2.0, testPlayer.getPlayerX());
        //just outside left x boundary
        testPlayer.setPlayerX(PLAYER_WIDTH / 2.0 - 1);
        testPlayer.enforceWall();
        assertEquals(PLAYER_WIDTH / 2.0, testPlayer.getPlayerX());
        //on left x boundary
        testPlayer.setPlayerX(PLAYER_WIDTH / 2.0);
        testPlayer.enforceWall();
        assertEquals(PLAYER_WIDTH / 2.0, testPlayer.getPlayerX());
        //just within left x boundary
        testPlayer.setPlayerX(PLAYER_WIDTH / 2.0 + 1);
        testPlayer.enforceWall();
        assertEquals(PLAYER_WIDTH / 2.0 + 1, testPlayer.getPlayerX());
        //about to move out of left boundary
        testPlayer.setPlayerX(PLAYER_WIDTH / 2.0 + 1);
        testPlayer.setDx(-2.0);
        testPlayer.move();
        testPlayer.enforceWall();
        assertEquals(PLAYER_WIDTH / 2.0, testPlayer.getPlayerX());
        assertEquals(0, testPlayer.getDx());
    }

    @Test
    void testRightEnforceWall() {
        //outside right x boundary
        testPlayer.setPlayerX(SCENE_WIDTH + PLAYER_WIDTH / 2.0 + 100);
        testPlayer.enforceWall();
        assertEquals(SCENE_WIDTH - PLAYER_WIDTH / 2.0, testPlayer.getPlayerX());
        //just outside right x boundary
        testPlayer.setPlayerX(SCENE_WIDTH - PLAYER_WIDTH / 2.0 + 1);
        testPlayer.enforceWall();
        assertEquals(SCENE_WIDTH - PLAYER_WIDTH / 2.0, testPlayer.getPlayerX());
        //on right x boundary
        testPlayer.setPlayerX(SCENE_WIDTH - PLAYER_WIDTH / 2.0);
        testPlayer.enforceWall();
        assertEquals(SCENE_WIDTH - PLAYER_WIDTH / 2.0, testPlayer.getPlayerX());
        //just within right x boundary
        testPlayer.setPlayerX(SCENE_WIDTH - PLAYER_WIDTH / 2.0 - 1);
        testPlayer.enforceWall();
        assertEquals(SCENE_WIDTH - PLAYER_WIDTH / 2.0 - 1, testPlayer.getPlayerX());
        //about to move out of right boundary
        testPlayer.setPlayerX(SCENE_WIDTH - PLAYER_WIDTH / 2.0 - 1);
        testPlayer.setDx(2.0);
        testPlayer.move();
        testPlayer.enforceWall();
        assertEquals(SCENE_WIDTH - PLAYER_WIDTH / 2.0, testPlayer.getPlayerX());
        assertEquals(0, testPlayer.getDx());
    }

    @Test
    void testBottomEnforceWall() {
        //outside bottom boundary
        testPlayer.setPlayerY(SCENE_HEIGHT + PLAYER_HEIGHT / 2.0 + 100);
        testPlayer.enforceWall();
        assertEquals(SCENE_HEIGHT - PLAYER_HEIGHT / 2.0, testPlayer.getPlayerY());
        //just outside bottom boundary
        testPlayer.setPlayerY(SCENE_HEIGHT - PLAYER_HEIGHT / 2.0 + 1);
        testPlayer.enforceWall();
        assertEquals(SCENE_HEIGHT - PLAYER_HEIGHT / 2.0, testPlayer.getPlayerY());
        //on bottom boundary
        testPlayer.setPlayerY(SCENE_HEIGHT - PLAYER_HEIGHT / 2.0);
        testPlayer.enforceWall();
        assertEquals(SCENE_HEIGHT - PLAYER_HEIGHT / 2.0, testPlayer.getPlayerY());
        //just within bottom boundary
        testPlayer.setPlayerY(SCENE_HEIGHT - PLAYER_HEIGHT / 2.0 - 1);
        testPlayer.enforceWall();
        assertEquals(SCENE_HEIGHT - PLAYER_HEIGHT / 2.0 - 1, testPlayer.getPlayerY());
        //about to move out of bottom boundary
        testPlayer.setPlayerY(SCENE_HEIGHT - PLAYER_HEIGHT / 2.0 - 1);
        testPlayer.setDy(2.0);
        testPlayer.setGravity(0);
        testPlayer.move();
        testPlayer.enforceWall();
        assertEquals(SCENE_HEIGHT - PLAYER_HEIGHT / 2.0, testPlayer.getPlayerY());
        assertEquals(testPlayer.getDy(), 0);
    }

    @Test
    void testTopEnforceWall() {
        //outside top boundary
        testPlayer.setPlayerY(-PLAYER_HEIGHT - 100);
        testPlayer.enforceWall();
        assertEquals(PLAYER_HEIGHT / 2.0, testPlayer.getPlayerY());
        //just outside top boundary
        testPlayer.setPlayerY(PLAYER_HEIGHT / 2.0 - 1);
        testPlayer.enforceWall();
        assertEquals(PLAYER_HEIGHT / 2.0, testPlayer.getPlayerY());
        //on top boundary
        testPlayer.setPlayerY(PLAYER_HEIGHT / 2.0);
        testPlayer.enforceWall();
        assertEquals(PLAYER_HEIGHT / 2.0, testPlayer.getPlayerY());
        //just within top boundary
        testPlayer.setPlayerY(PLAYER_HEIGHT / 2.0 + 1);
        testPlayer.enforceWall();
        assertEquals(PLAYER_HEIGHT / 2.0 + 1, testPlayer.getPlayerY());
        //about to move out of left boundary
        testPlayer.setPlayerY(PLAYER_HEIGHT / 2.0 + 1);
        testPlayer.setDy(-2.0);
        testPlayer.setGravity(0);
        testPlayer.move();
        testPlayer.enforceWall();
        assertEquals(PLAYER_HEIGHT / 2.0, testPlayer.getPlayerY());
        assertEquals(0, testPlayer.getDy());
    }

    @Test
    void testReload() {
        assertEquals(testPlayer.getMagazine(), 0);
        testPlayer.reload();
        assertEquals(Player.RELOAD_AMOUNT, testPlayer.getMagazine());
    }

    @Test
    void testPelletXStart() {
        testPlayer.setFacingRight(true);
        assertEquals(P1_STARTX + (PLAYER_WIDTH / 2.0) + PELLET_WIDTH / 2 + 1, testPlayer.pelletXStart());
        testPlayer.setFacingRight(false);
        assertEquals(P1_STARTX - (PLAYER_WIDTH / 2.0) - PELLET_WIDTH / 2 - 1, testPlayer.pelletXStart());
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
    }

    @Test
    void testJump() {
        //in air
        testPlayer.setPlayerY(SCENE_HEIGHT / 2.0);
        testPlayer.jump();
        assertEquals(SCENE_HEIGHT / 2.0,testPlayer.getPlayerY());
        testPlayer.setPlayerY(P_STARTY);
        testPlayer.setGravity(100);
        testPlayer.jump();
        //about to jump
        assertEquals(JUMP_STRENGTH, testPlayer.getDy());
        assertEquals(P_STARTY,testPlayer.getPlayerY());
        //off the ground
        testPlayer.move();
        assertEquals(JUMP_STRENGTH + 100, testPlayer.getDy());
        assertEquals(P_STARTY - 100, testPlayer.getPlayerY());
        //about to come back down
        testPlayer.move();
        assertEquals(JUMP_STRENGTH + 200, testPlayer.getDy());
        assertEquals(P_STARTY - 100, testPlayer.getPlayerY());
    }

    @Test
    void testCheckIfGotShotBy() {
        Player testPlayer2 = new Player(P2_STARTX, P_STARTY);
        testPlayer.setFacingRight(true);
        testPlayer.reload();
        testPlayer.shoot();
        assertFalse(testPlayer2.checkifGotShotBy(testPlayer));
        testPlayer.getPellets().getElement(0).setPelletX(P2_STARTX);
        assertTrue(testPlayer2.checkifGotShotBy(testPlayer));
    }
}
