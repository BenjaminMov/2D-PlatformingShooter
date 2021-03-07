package persistence;

import model.Level;
import model.LevelBank;
import model.Platform;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/SomeUnknownFile.json");
        try {
            LevelBank lvls = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLevelBank() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLevelBank.json");
        try {
            LevelBank lvlbnk = reader.read();
            assertEquals(0, lvlbnk.getAllLevels().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/TestGenericLevelBankTwoLevels.json");
        try {
            LevelBank lvlbnk = reader.read();
            List<Level> levels = lvlbnk.getAllLevels();
            List<Platform> platforms1 = levels.get(0).getPlatforms();
            List<Platform> platforms2 = levels.get(1).getPlatforms();

            assertEquals(2, levels.size());
            checkLevel("testlevel1", levels.get(0), platforms1);
            checkLevel("testlevel2", levels.get(1), platforms2);

            checkPlatform(1, 2, 3, levels.get(0), 0);
            checkPlatform(4, 5, 6, levels.get(1), 0);
            checkPlatform(10, 20, 30, levels.get(1), 1);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}


