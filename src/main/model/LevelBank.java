package model;

import exceptions.NoSuchLevelNameException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Class that holds all levels
public class LevelBank implements Writable {

    private final List<Level> allLevels = new ArrayList();

    public List<Level> getAllLevels() {
        return allLevels;
    }

    //EFFECTS: writes this levelBank to Json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("levels", levelToJson());
        return json;
    }

    //EFFECTS: returns levels in this levelBank as a JSON array
    private JSONArray levelToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Level lvl : allLevels) {
            jsonArray.put(lvl.toJson());
        }
        return jsonArray;
    }

    //MODIFIES: this
    //EFFECTS: adds a level to levelBank
    public void addLevel(Level level) {
        allLevels.add(level);
    }

    //EFFECTS: searches through all levels to find a level with a matching name
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
