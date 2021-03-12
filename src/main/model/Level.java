package model;

import exceptions.NoRemainingPlatformException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class Level implements Writable {

    private final List<Platform> allPlatforms;
    private String levelName;

    public Level(String levelName) {
        allPlatforms = new ArrayList<>();
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }

    public Platform getPlatform(int i) {
        return allPlatforms.get(i);
    }

    public List<Platform> getPlatforms() {
        return allPlatforms;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void addPlatform(Platform platform) {
        allPlatforms.add(platform);
    }

    public void removeLastPlatform() throws NoRemainingPlatformException {
        if (allPlatforms.size() == 0) {
            throw new NoRemainingPlatformException();
        } else {
            allPlatforms.remove(allPlatforms.size() - 1);
        }
    }

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

    public void makePlatformsSolid(Player player) {
        boolean myBool = checkIfOnAnyPlatforms(player);
        player.setOnPlatform(myBool);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("levelName", levelName);
        json.put("platforms", platformsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray platformsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Platform p : allPlatforms) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}


