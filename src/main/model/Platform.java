package model;

import org.json.JSONObject;
import persistence.Writable;

public class Platform implements Writable {

    private int platformX;
    private int platformY;
    private int platformWidth;

    public static final int PLATFORM_HEIGHT = 3;

    public Platform(int x, int y, int width) {
        platformX = x;
        platformY = y;
        platformWidth = width;
    }

    //getters
    public int getPlatformX() {
        return platformX;
    }

    public int getPlatformY() {
        return platformY;
    }

    public int getPlatformWidth() {
        return platformWidth;
    }

    //setters
    public void setPlatformWidth(int width) {
        platformWidth = width;
    }

    //EFFECTS: if the player is within the x bounds of the platform, return true; else false
    private boolean inXRange(Player player) {
        return player.getPlayerX() + Player.PLAYER_WIDTH / 2 >= platformX - platformWidth / 2
                && player.getPlayerX() - Player.PLAYER_WIDTH / 2 <= platformX + platformWidth / 2;
    }

    //EFFECTS: return if the player is about to fall through the platform, or is already on the platform
    private boolean inYRange(Player player) {
        double platformTop = platformY - PLATFORM_HEIGHT;
        double playerBottom = player.getPlayerY() + Player.PLAYER_HEIGHT / 2;
        boolean abovePlatform = playerBottom <= platformTop;
        boolean nextFrameUnder = (player.getGravity() + player.getDy() + playerBottom) >= platformTop;

        return (abovePlatform && nextFrameUnder) || ((playerBottom == platformTop) && (player.getDy() == 0));
    }

    //EFFECTS: checks if player is in both x and y range, if so then set their y pos to top of platform.
    // if the player is falling set Dy to 0.
    public boolean checkIfOnPlatform(Player player) {
        boolean inRange = inYRange(player) && inXRange(player);
        if (inRange) {
            player.setPlayerY(platformY - Player.PLAYER_HEIGHT / 2 - PLATFORM_HEIGHT);
        }
        if (inRange && (player.getDy() > 0)) {
            player.setDy(0);
        }
        return inRange;
    }

    //EFFECTS: writes this platform object to Json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", platformX);
        json.put("y", platformY);
        json.put("width", platformWidth);
        return json;
    }

}
