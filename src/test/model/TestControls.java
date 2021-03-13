package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static model.World.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestControls {

    private World testWorld;
    private int testStartX;

    @BeforeEach
    public void runBefore() {
        testWorld = new World();
        testStartX = SCENE_WIDTH / 2;
    }

    @Test
    public void testPlayer1KeyEvent() {
        Player p1 = testWorld.getPlayer1();
        //Going Left
        p1.setPlayerX(testStartX);
        testWorld.keyPressed(KeyEvent.VK_A);
        testWorld.update();
        assertEquals(-SPEEDX, p1.getDx());
        assertEquals(testStartX - SPEEDX, p1.getPlayerX());
        assertFalse(p1.isFacingRight());
        testWorld.update();
        assertEquals(-SPEEDX, p1.getDx());
        assertEquals(testStartX - 2 * SPEEDX, p1.getPlayerX());
        p1.setPlayerX(testStartX);
        //Going Right
        testWorld.keyPressed(KeyEvent.VK_D);
        testWorld.update();
        assertEquals(SPEEDX, p1.getDx());
        assertEquals(testStartX + SPEEDX, p1.getPlayerX());
        assertTrue(p1.isFacingRight());
        testWorld.update();
        assertEquals(SPEEDX, p1.getDx());
        assertEquals(testStartX + 2 * SPEEDX, p1.getPlayerX());
        p1.setPlayerX(testStartX);
        //jumping
        assertEquals(SCENE_HEIGHT - Player.PLAYER_HEIGHT / 2, p1.getPlayerY());
        assertEquals(0, p1.getDy());
        testWorld.keyPressed(KeyEvent.VK_W);
        testWorld.update();
        assertEquals(SCENE_HEIGHT - Player.PLAYER_HEIGHT / 2
                + Player.JUMP_STRENGTH + Player.NORMAL_GRAVITY, p1.getPlayerY());
        //Reloading
        testWorld.keyPressed(KeyEvent.VK_J);
        testWorld.update();
        assertFalse(p1.isCanMove());
        assertEquals(0, p1.getMagazine());
        try {
            Thread.sleep(RELOAD_DELAY + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(Player.RELOAD_AMOUNT, p1.getMagazine());
        assertTrue(p1.isCanMove());

        assertEquals(0, p1.getPellets().size());
        testWorld.keyPressed(KeyEvent.VK_K);
        assertEquals(1, p1.getPellets().size());
    }

    @Test
    public void testPlayer2KeyEvent() {
        Player p2 = testWorld.getPlayer2();
        //Going Left
        p2.setPlayerX(testStartX);
        testWorld.keyPressed(KeyEvent.VK_LEFT);
        testWorld.update();
        assertEquals(-SPEEDX, p2.getDx());
        assertEquals(testStartX - SPEEDX, p2.getPlayerX());
        assertFalse(p2.isFacingRight());
        testWorld.update();
        assertEquals(-SPEEDX, p2.getDx());
        assertEquals(testStartX - 2 * SPEEDX, p2.getPlayerX());
        p2.setPlayerX(testStartX);
        //Going Right
        testWorld.keyPressed(KeyEvent.VK_RIGHT);
        testWorld.update();
        assertEquals(SPEEDX, p2.getDx());
        assertEquals(testStartX + SPEEDX, p2.getPlayerX());
        assertTrue(p2.isFacingRight());
        testWorld.update();
        assertEquals(SPEEDX, p2.getDx());
        assertEquals(testStartX + 2 * SPEEDX, p2.getPlayerX());
        p2.setPlayerX(testStartX);
        //jumping
        assertEquals(SCENE_HEIGHT - Player.PLAYER_HEIGHT / 2, p2.getPlayerY());
        assertEquals(0, p2.getDy());
        testWorld.keyPressed(KeyEvent.VK_UP);
        testWorld.update();
        assertEquals(SCENE_HEIGHT - Player.PLAYER_HEIGHT / 2
                   + Player.JUMP_STRENGTH + Player.NORMAL_GRAVITY, p2.getPlayerY());
        //Reloading
        testWorld.keyPressed(KeyEvent.VK_9);
        testWorld.update();
        assertEquals(0, p2.getMagazine());
        try {
            Thread.sleep(RELOAD_DELAY + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(Player.RELOAD_AMOUNT, p2.getMagazine());

        p2.setMagazine(0);
        testWorld.keyPressed(KeyEvent.VK_NUMPAD4);
        testWorld.update();
        assertFalse(p2.isCanMove());
        assertEquals(0, p2.getMagazine());
        try {
            Thread.sleep(RELOAD_DELAY + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(Player.RELOAD_AMOUNT, p2.getMagazine());
        assertTrue(p2.isCanMove());

        assertEquals(0, p2.getPellets().size());
        testWorld.keyPressed(KeyEvent.VK_0);
        assertEquals(1, p2.getPellets().size());

        assertEquals(1, p2.getPellets().size());
        testWorld.keyPressed(KeyEvent.VK_NUMPAD5);
        assertEquals(2, p2.getPellets().size());
    }

    @Test
    void testCleanup() {
        testWorld.getPlayer1().setPlayerX(93);
        testWorld.getPlayer2().setPlayerX(56);
        testWorld.getPlayer1().setPlayerY(100);
        testWorld.getPlayer2().setPlayerY(100);
        testWorld.getPlayer1().setMagazine(10);
        testWorld.getPlayer2().setMagazine(6);
        testWorld.setIfGameOver(true);
        testWorld.keyPressed(KeyEvent.VK_R);

        assertEquals(P1_STARTX, testWorld.getPlayer1().getPlayerX());
        assertEquals(P2_STARTX, testWorld.getPlayer2().getPlayerX());
        assertEquals(P_STARTY, testWorld.getPlayer1().getPlayerY());
        assertEquals(P_STARTY, testWorld.getPlayer2().getPlayerY());
        assertEquals(0, testWorld.getPlayer1().getMagazine());
        assertEquals(0, testWorld.getPlayer2().getMagazine());
        assertFalse(testWorld.getIfGameOver());
    }

    @Test
    void testKeyReleased() {
        testWorld.getPlayer1().setDx(40);
        testWorld.getPlayer2().setDx(1);
        testWorld.keyReleased(KeyEvent.VK_A);
        assertEquals(0, testWorld.getPlayer1().getDx());
        assertEquals(1, testWorld.getPlayer2().getDx());
        testWorld.getPlayer1().setDx(30);
        testWorld.keyReleased(KeyEvent.VK_LEFT);
        assertEquals(30, testWorld.getPlayer1().getDx());
        assertEquals(0, testWorld.getPlayer2().getDx());
        testWorld.getPlayer2().setDx(2);
        testWorld.keyReleased(KeyEvent.VK_D);
        assertEquals(0, testWorld.getPlayer1().getDx());
        assertEquals(2, testWorld.getPlayer2().getDx());
        testWorld.getPlayer1().setDx(20);
        testWorld.keyReleased(KeyEvent.VK_RIGHT);
        assertEquals(20, testWorld.getPlayer1().getDx());
        assertEquals(0, testWorld.getPlayer2().getDx());
        testWorld.getPlayer2().setDx(2);
    }

}
