package model;

import org.json.JSONObject;

public class Platform {

    private double platformX;
    private double platformY;
    private double platformWidth;

    public static final double PLATFORM_HEIGHT = 2;

    public Platform(double x, double y, double width) {
        platformX = x;
        platformY = y;
        platformWidth = width;
    }

    private boolean inXRange(Player player) {
        boolean result = player.getPlayerX() >= platformX - platformWidth / 2
                && player.getPlayerX() <= platformX + platformWidth / 2;
        return result;
    }

    private boolean inYRange(Player player) {
        return player.getPlayerY() == platformY - PLATFORM_HEIGHT;
    }

    public boolean checkIfOnPlatform(Player player) {
        return  inYRange(player) && inXRange(player);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", platformX);
        json.put("y", platformY);
        json.put("width", platformWidth);
        return json;
    }

}
