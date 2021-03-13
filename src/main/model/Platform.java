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

    public int getPlatformX() {
        return platformX;
    }

    public int getPlatformY() {
        return platformY;
    }

    public int getPlatformWidth() {
        return platformWidth;
    }

    public void setPlatformWidth(int width) {
        platformWidth = width;
    }

    private boolean inXRange(Player player) {
        return player.getPlayerX() + Player.PLAYER_WIDTH / 2 >= platformX - platformWidth / 2
                && player.getPlayerX() - Player.PLAYER_WIDTH / 2 <= platformX + platformWidth / 2;
    }

    private boolean inYRange(Player player) {
        double platformTop = platformY - PLATFORM_HEIGHT;
        double playerBottom = player.getPlayerY() + Player.PLAYER_HEIGHT / 2;
        boolean abovePlatform = playerBottom <= platformTop;
        boolean nextFrameUnder = (player.getGravity() + player.getDy() + playerBottom) >= platformTop;

        return (abovePlatform && nextFrameUnder) || ((playerBottom == platformTop) && (player.getDy() == 0));
    }

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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", platformX);
        json.put("y", platformY);
        json.put("width", platformWidth);
        return json;
    }

}
