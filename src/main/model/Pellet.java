package model;

// Model of an object that is shot by a player
public class Pellet {

    private int pelletX;
    private int pelletY;
    private int dx;

    public static final int PELLET_WIDTH = 4;
    public static final int PELLET_HEIGHT = 4;

    public static final int SHOT_SPEED = 30;

    // EFFECTS: Creates a new Pellet object
    public Pellet(int x, int y, int dx) {
        pelletX = x;
        pelletY = y;
        this.dx = dx;

    }

    public int getPelletX() {
        return pelletX;
    }

    public int getPelletY() {
        return pelletY;
    }

    public int getDx() {
        return dx;
    }

    public void setPelletX(int pelletX) {
        this.pelletX = pelletX;
    }

    public void setPelletY(int pelletY) {
        this.pelletY = pelletY;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    // MODIFIES: this
    // EFFECTS: adds the pellet's velocity to the x position
    public void move() {
        pelletX += dx;
    }

}
