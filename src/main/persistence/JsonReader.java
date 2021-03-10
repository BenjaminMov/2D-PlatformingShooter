package persistence;

import model.Level;
import model.LevelBank;
import model.Platform;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public LevelBank read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLevelBank(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private LevelBank parseLevelBank(JSONObject jsonObject) {
        LevelBank lvlbnk = new LevelBank();
        addLevels(lvlbnk, jsonObject);
        return lvlbnk;
    }

    private void addLevels(LevelBank lvlbnk, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("levels");
        for (Object json : jsonArray) {
            JSONObject nextLevel = (JSONObject) json;
            addLevel(lvlbnk, nextLevel);
        }
    }

    private void addLevel(LevelBank lvls, JSONObject jsonObject) {
        String name = jsonObject.getString("levelName");
        Level lvl = new Level(name);
        addPlatforms(lvl, jsonObject);
        lvls.addLevel(lvl);
    }

    // MODIFIES: wr
    // EFFECTS: parses platforms from JSON object and adds them to Level
    private void addPlatforms(Level lvl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("platforms");
        for (Object json : jsonArray) {
            JSONObject nextPlatform = (JSONObject) json;
            addPlatform(lvl, nextPlatform);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addPlatform(Level lvl, JSONObject jsonObject) {
        int platformX = jsonObject.getInt("x");
        int platformY = jsonObject.getInt("y");
        int platformWidth = jsonObject.getInt("width");
        Platform platform = new Platform(platformX, platformY, platformWidth);
        lvl.addPlatform(platform);
    }
}
