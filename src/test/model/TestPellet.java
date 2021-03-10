package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestPellet {

    private static final int MIDDLE_X = World.SCENE_WIDTH / 2;
    private static final int MIDDLE_Y = World.SCENE_HEIGHT / 2;
    Pellet testPellet;

    @BeforeEach
    void runBefore() {
        testPellet = new Pellet(0, 0, 0);
    }

    @Test
    void testMove(){
        testPellet.setDx(Pellet.SHOT_SPEED);
        testPellet.setPelletX(MIDDLE_X);
        testPellet.setPelletY(MIDDLE_Y);
        assertEquals(Pellet.SHOT_SPEED, testPellet.getDx());
        assertEquals(MIDDLE_X, testPellet.getPelletX());
        assertEquals(MIDDLE_Y, testPellet.getPelletY());
        testPellet.move();
        assertEquals(MIDDLE_X + Pellet.SHOT_SPEED, testPellet.getPelletX());
        assertEquals(MIDDLE_Y, testPellet.getPelletY());
    }
}
