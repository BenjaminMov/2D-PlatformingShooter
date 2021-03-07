package persistence;

import model.LevelBank;
import model.Level;
import model.Platform;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkLevel(String levelName, Level level, List<Platform> lop) {
        assertEquals(levelName, level.getLevelName());
        assertEquals(lop.size(), level.getPlatforms().size());
    }

    protected void checkPlatform(double x, double y, double width, Level level, int platformIndex) {
        assertEquals(x, level.getPlatform(platformIndex).getPlatformX());
        assertEquals(y, level.getPlatform(platformIndex).getPlatformY());
        assertEquals(width, level.getPlatform(platformIndex).getPlatformWidth());
    }
}