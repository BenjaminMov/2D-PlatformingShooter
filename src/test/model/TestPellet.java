package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestPellet {

    private static final double MIDDLEX = World.SCENE_WIDTH / 2.0;
    private static final double MIDDLEY = World.SCENE_HEIGHT / 2.0;
    Pellet testPellet;

    @BeforeEach
    void runBefore() {
        testPellet = new Pellet(0, 0, 0);
    }

    @Test
    void testMove(){
        testPellet.setDx(Pellet.SHOT_SPEED);
        testPellet.setPelletX(MIDDLEX);
        testPellet.setPelletY(MIDDLEY);
        assertEquals(Pellet.SHOT_SPEED, testPellet.getDx());
        assertEquals(MIDDLEX, testPellet.getPelletX());
        assertEquals(MIDDLEY, testPellet.getPelletY());
        testPellet.move();
        assertEquals(MIDDLEX + Pellet.SHOT_SPEED, testPellet.getPelletX());
        assertEquals(MIDDLEY, testPellet.getPelletY());
    }
}
