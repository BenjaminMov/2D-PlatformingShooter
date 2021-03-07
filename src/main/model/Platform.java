package model;

import org.json.JSONObject;
import persistence.Writable;

public class Platform implements Writable {

    private double platformX;
    private double platformY;
    private double platformWidth;

    public static final double PLATFORM_HEIGHT = 2;

    public Platform(double x, double y, double width) {
        platformX = x;
        platformY = y;
        platformWidth = width;
    }

    public double getPlatformX() {
        return platformX;
    }

    public double getPlatformY() {
        return platformY;
    }

    public double getPlatformWidth() {
        return platformWidth;
    }

    private boolean inXRange(Player player) {
        return player.getPlayerX() + Player.PLAYER_WIDTH / 2.0 >= platformX - platformWidth / 2
                && player.getPlayerX() - Player.PLAYER_WIDTH / 2.0 <= platformX + platformWidth / 2;
    }

    private boolean inYRange(Player player) {
        double platformTop = platformY - PLATFORM_HEIGHT;
        double playerBottom = player.getPlayerY() + Player.PLAYER_HEIGHT / 2.0;
        boolean abovePlatform = playerBottom <= platformTop;
        boolean nextFrameUnder = (player.getGravity() + player.getDy() + playerBottom) >= platformTop;

        return (abovePlatform && nextFrameUnder) || ((playerBottom == platformTop) && (player.getDy() == 0));
    }

    public boolean checkIfOnPlatform(Player player) {
        boolean inRange = inYRange(player) && inXRange(player);
        if (inRange) {
            player.setPlayerY(platformY - Player.PLAYER_HEIGHT / 2.0 - PLATFORM_HEIGHT);
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
