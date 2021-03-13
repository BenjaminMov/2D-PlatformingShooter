package model;

import exceptions.NoRemainingPlatformException;
import exceptions.NoSuchLevelNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestLevel {

    LevelBank testLevelBank;
    Level testLevel1;
    Level testLevel2;
    Level testLevel3;
    Level testLevel4;

    @BeforeEach
    void runBefore() {
        testLevelBank = new LevelBank();

        testLevel1 = new Level("hellooooLvl1");
        testLevel2 = new Level("goodbyeeLvl2");
        testLevel3 = new Level("someWordLvl3");
        testLevel4 = new Level("LastWordLvl4");

        testLevel3.addPlatform(new Platform(10,10,10));

        testLevel4.addPlatform(new Platform(20,20,20));
        testLevel4.addPlatform(new Platform(40,50,60));

        testLevelBank.addLevel(testLevel1);
        testLevelBank.addLevel(testLevel2);
        testLevelBank.addLevel(testLevel3);
        testLevelBank.addLevel(testLevel4);
    }

    @Test
    void testFindLevel() {
        try {
            assertEquals(testLevel1, testLevelBank.findLevel("hellooooLvl1"));
            assertEquals(0, testLevel1.getPlatforms().size());
            assertEquals("hellooooLvl1", testLevelBank.findLevel("hellooooLvl1").getLevelName());
            testLevel1.setLevelName("byebye");
            assertEquals("byebye", testLevelBank.findLevel("byebye").getLevelName());
        } catch (NoSuchLevelNameException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testFindOtherLevel() {
        try {
            assertEquals(testLevel3, testLevelBank.findLevel("someWordLvl3"));
            assertEquals("someWordLvl3", testLevelBank.findLevel("someWordLvl3").getLevelName());
            assertEquals(1, testLevel3.getPlatforms().size());
            assertEquals(10, testLevel3.getPlatform(0).getPlatformX());
            assertEquals(10, testLevel3.getPlatform(0).getPlatformY());
            assertEquals(10, testLevel3.getPlatform(0).getPlatformWidth());
        } catch (NoSuchLevelNameException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testRemoveLastPlatformEmptyLevel() {
        try {
            testLevel1.removeLastPlatform();
            fail();
        } catch (NoRemainingPlatformException e) {
            // :)
        }
    }

    @Test
    void testRemoveLastPlatformOneLevel() {
        try {
            testLevel3.removeLastPlatform();
            assertEquals(0, testLevel3.getPlatforms().size());
        } catch (NoRemainingPlatformException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testRemoveLastPlatformTwoLevels() {
        try {
            testLevel4.removeLastPlatform();
            assertEquals(1, testLevel4.getPlatforms().size());
            assertEquals(20, testLevel4.getPlatform(0).getPlatformX());
            assertEquals(20, testLevel4.getPlatform(0).getPlatformY());
            assertEquals(20, testLevel4.getPlatform(0).getPlatformWidth());
        } catch (NoRemainingPlatformException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testNoSuchLevelNameException() {
        try {
            assertEquals(testLevel1, testLevelBank.findLevel("notARealLevelName"));
            fail();
        } catch (NoSuchLevelNameException e) {
            // :)
        }
    }
}
