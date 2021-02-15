package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPellets {

    Pellets testPellets;
    Pellet testP1;
    Pellet testP2;
    Pellet testP3;

    @BeforeEach
    void runBefore() {
        testPellets = new Pellets();
        testP1 = new Pellet(10.0,10.0,10.0);
        testP2 = new Pellet(50.0, 50.0, 20.0);
        testP3 = new Pellet(15.0, 25.0, 60.0);

        testPellets.addPellet(testP1);
        testPellets.addPellet(testP2);
        testPellets.addPellet(testP3);
    }

    @Test
    void testMovePelletsX() {
        //check x coords
        ArrayList<Double> pelletListX = new ArrayList<>();
        pelletListX.add(10.0);
        pelletListX.add(50.0);
        pelletListX.add(15.0);
        assertEquals(pelletListX, testPellets.getAllPelletX());
        testPellets.movePellets();
        pelletListX.clear();
        pelletListX.add(10.0 + 10.0);
        pelletListX.add(50.0 + 20.0);
        pelletListX.add(15.0 + 60.0);
        assertEquals(pelletListX, testPellets.getAllPelletX());
    }

    @Test
    void testMovePelletsY() {
        //check y coords
        ArrayList<Double> pelletListY = new ArrayList<>();
        pelletListY.add(10.0);
        pelletListY.add(50.0);
        pelletListY.add(25.0);
        assertEquals(pelletListY, testPellets.getAllPelletY());
        //no movement in y expected
        testPellets.movePellets();
        assertEquals(pelletListY, testPellets.getAllPelletY());
    }
}
