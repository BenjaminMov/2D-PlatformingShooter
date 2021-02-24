package model;

import java.util.ArrayList;
import java.util.List;

public class Level {

    List<Platform> allPlatforms;

    public Level() {
        allPlatforms = new ArrayList<>();
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
        player.setOnPlatform(checkIfOnAnyPlatforms(player));
    }
}


