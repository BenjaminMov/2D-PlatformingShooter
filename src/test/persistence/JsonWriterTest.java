package persistence;

import model.Level;
import model.LevelBank;
import model.Platform;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            LevelBank lvlbnk = new LevelBank();
            JsonWriter writer = new JsonWriter("./data/bing\0bong.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            LevelBank lvlbnk = new LevelBank();
            JsonWriter writer = new JsonWriter("./data/TestWriterEmptyLevelBank.json");
            writer.open();
            writer.write(lvlbnk);
            writer.close();

            JsonReader reader = new JsonReader("./data/TestWriterEmptyLevelBank.json");
            lvlbnk = reader.read();
            assertEquals(0, lvlbnk.getAllLevels().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            LevelBank testLvlbnk = new LevelBank();
            Level testLevel1 = new Level("NewTestLevel1");
            Level testLevel2 = new Level("NewTestLevel2");
            Platform testPlatform1 = new Platform(1,1,1);
            Platform testPlatform2 = new Platform(2,2,2);
            Platform testPlatform3 = new Platform(3,3,3);
            List<Platform> testLOP1 = new ArrayList<>();
            List<Platform> testLOP2 = new ArrayList<>();

            testLvlbnk.addLevel(testLevel1);
            testLvlbnk.addLevel(testLevel2);
            testLevel1.addPlatform(testPlatform1);
            testLevel1.addPlatform(testPlatform2);
            testLevel2.addPlatform(testPlatform3);
            testLOP1.add(testPlatform1);
            testLOP1.add(testPlatform2);
            testLOP2.add(testPlatform3);


            JsonWriter writer = new JsonWriter("./data/TestWriterGeneralLevelBank.json");
            writer.open();
            writer.write(testLvlbnk);
            writer.close();

            JsonReader reader = new JsonReader("./data/TestWriterGeneralLevelBank.json");
            testLvlbnk = reader.read();
            List<Level> levels = testLvlbnk.getAllLevels();
            assertEquals(2, levels.size());
            checkLevel("NewTestLevel1", levels.get(0), testLOP1);
            checkLevel("NewTestLevel2", levels.get(1), testLOP2);
            checkPlatform(1,1,1,testLevel1,0);
            checkPlatform(2,2,2,testLevel1,1);
            checkPlatform(3,3,3,testLevel2,0);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
