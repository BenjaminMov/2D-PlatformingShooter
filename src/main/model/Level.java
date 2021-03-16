package model;

import exceptions.NoRemainingPlatformException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//A level that holds platforms and has a name
public class Level implements Writable {

    private final List<Platform> allPlatforms;
    private String levelName;

    public Level(String levelName) {
        allPlatforms = new ArrayList<>();
        this.levelName = levelName;
    }

    //getters
    public String getLevelName() {
        return levelName;
    }

    public Platform getPlatform(int i) {
        return allPlatforms.get(i);
    }

    public List<Platform> getPlatforms() {
        return allPlatforms;
    }

    //setters
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    //MODIFIES: this
    //EFFECTS: adds a platform to the level
    public void addPlatform(Platform platform) {
        allPlatforms.add(platform);
    }

    //MODIFIES: this
    //EFFECTS: removes the last added platform from this level
    public void removeLastPlatform() throws NoRemainingPlatformException {
        if (allPlatforms.size() == 0) {
            throw new NoRemainingPlatformException();
        } else {
            allPlatforms.remove(allPlatforms.size() - 1);
        }
    }

    //EFFECTS: returns if a given player is on any platforms in the level
    public boolean checkIfOnAnyPlatforms(Player player) {
        boolean onPlatform = false;
        for (Platform p : allPlatforms) {
            if (p.checkIfOnPlatform(player)) {
                onPlatform = true;
                break;
            }
        }
        return onPlatform;
    }

    //EFFECTS: if player is on a platform, make their y velocity 0
    public void makePlatformsSolid(Player player) {
        boolean myBool = checkIfOnAnyPlatforms(player);
        player.setOnPlatform(myBool);
    }

    //EFFECTS: writes this to Json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("levelName", levelName);
        json.put("platforms", platformsToJson());
        return json;
    }

    // EFFECTS: returns platforms in this level as a JSON array
    private JSONArray platformsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Platform p : allPlatforms) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}


