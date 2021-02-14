package model;

// Model of an object that is shot by a player
public class Pellet {

    private double pelletX;
    private double pelletY;
    private double dx;

    public static final double PELLET_WIDTH = 3;

    public static final int SHOT_SPEED = 30;

    // EFFECTS: Creates a new Pellet object
    public Pellet(double x,double y,double dx) {
        pelletX = x;
        pelletY = y;
        this.dx = dx;

    }

    public double getPelletX() {
        return pelletX;
    }

    public double getPelletY() {
        return pelletY;
    }

    public double getDx() {
        return dx;
    }

    public void setPelletX(double pelletX) {
        this.pelletX = pelletX;
    }

    public void setPelletY(double pelletY) {
        this.pelletY = pelletY;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    // MODIFIES: this
    // EFFECTS: adds the pellet's velocity to the x position
    public void move() {
        pelletX += dx;
    }

}
