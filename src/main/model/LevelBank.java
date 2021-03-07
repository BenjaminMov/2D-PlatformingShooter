package model;

import exceptions.NoSuchLevelNameException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class LevelBank implements Writable {

    private final List<Level> allLevels = new ArrayList();

    public List<Level> getAllLevels() {
        return allLevels;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("levels", levelToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray levelToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Level lvl : allLevels) {
            jsonArray.put(lvl.toJson());
        }
        return jsonArray;
    }

    public void addLevel(Level level) {
        allLevels.add(level);
    }

    public Level findLevel(String levelName) throws NoSuchLevelNameException {
        Level foundLevel = null;
        for (Level level : allLevels) {
            if (level.getLevelName().equals(levelName)) {
                foundLevel = level;
                break;
            }
        }
        if (foundLevel == null) {
            throw new NoSuchLevelNameException("could not find level name");
        } else {
            return foundLevel;
        }
    }
}
