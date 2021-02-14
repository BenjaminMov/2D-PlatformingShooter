package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPellets {

    Pellets testpellets;
    Pellet testP1;
    Pellet testP2;
    Pellet testP3;

    @BeforeEach
    void runBefore() {
        testpellets = new Pellets();
        testP1 = new Pellet(10.0,10.0,10.0);
        testP2 = new Pellet(50.0, 50.0, 20.0);
        testP3 = new Pellet(15.0, 25.0, 60.0);

        testpellets.addPellet(testP1);
        testpellets.addPellet(testP2);
        testpellets.addPellet(testP3);
    }

    @Test
    void testMovePelletsX() {
        //check x coords
        ArrayList<Double> pelletListX = new ArrayList<>();
        pelletListX.add(10.0);
        pelletListX.add(50.0);
        pelletListX.add(15.0);
        assertEquals(pelletListX, testpellets.getAllPelletX());
        testpellets.movePellets();
        pelletListX.clear();
        pelletListX.add(10.0 + 10.0);
        pelletListX.add(50.0 + 20.0);
        pelletListX.add(15.0 + 60.0);
        assertEquals(pelletListX, testpellets.getAllPelletX());
    }

    @Test
    void testMovePelletsY() {
        //check y coords
        ArrayList<Double> pelletListY = new ArrayList<>();
        pelletListY.add(10.0);
        pelletListY.add(50.0);
        pelletListY.add(25.0);
        assertEquals(pelletListY, testpellets.getAllPelletY());
        //no movement in y expected
        testpellets.movePellets();
        assertEquals(pelletListY, testpellets.getAllPelletY());
    }
}
