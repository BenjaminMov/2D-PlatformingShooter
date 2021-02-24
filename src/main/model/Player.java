package model;

// Model of a player than can be controlled by the user
public class Player {
    private double playerX;
    private double playerY;
    private double dx;
    private double dy;
    private double gravity = 100;
    private Pellets pellets;

    private int magazine = 0;
    private boolean facingRight;
    private boolean onPlatform;
    private boolean alive = true;

    public static final int PLAYER_WIDTH = 16;
    public static final int PLAYER_HEIGHT = 20;
    public static final int RELOAD_AMOUNT = 3;
    public static final double JUMP_STRENGTH = -200;



    public Player(double x, double y) {
        this.playerX = x;
        this.playerY = y;
        dx = 0;
        dy = 0;
        pellets = new Pellets();
    }

    //getters
    public double getPlayerX() {
        return playerX;
    }

    public double getPlayerY() {
        return playerY;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public Integer getMagazine() {
        return magazine;
    }

    public Pellets getPellets() {
        return pellets;
    }

    public boolean getAlive() {
        return alive;
    }

    public boolean getFacingRight() {
        return facingRight;
    }


    //setters
    public void setPlayerX(double playerX) {
        this.playerX = playerX;
    }

    public void setPlayerY(double playerY) {
        this.playerY = playerY;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public void setOnPlatform(boolean onPlatform) {
        this.onPlatform = onPlatform;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    //MODIFIES: playerX, playerY, dy
    //EFFECTS: gives the next position of the player
    public void move() {
        if (playerY <= World.P_STARTY && !onPlatform) { //change to include platforms aswell
            dy = dy + gravity;
        }
        playerX = playerX + dx;
        playerY = playerY + dy;
    }

    //MODIFIES: playerX, playerY
    //EFFECTS: Ensures the player is unable to leave the bounds of the screen
    public void enforceWall() {
        if (playerX - PLAYER_WIDTH / 2.0 + dx < 0) {
            playerX = PLAYER_WIDTH / 2.0;
            dx = 0;
        } else if (playerX + PLAYER_WIDTH / 2.0 + dx > World.SCENE_WIDTH) {
            playerX = World.SCENE_WIDTH - PLAYER_WIDTH / 2.0;
            dx = 0;
        }
        if (playerY - PLAYER_HEIGHT / 2.0 + dy < 0) {
            playerY = PLAYER_HEIGHT / 2.0;
            dy = 0;
        } else if (playerY + PLAYER_HEIGHT / 2.0 + dy > World.SCENE_HEIGHT) {
            playerY = World.SCENE_HEIGHT - PLAYER_HEIGHT / 2.0;
            dy = 0;
        }
    }

    // MODIFIES: magazine
    // EFFECTS: adds the constant RELOAD_AMOUNT to magazine field
    public void reload() {
        magazine += RELOAD_AMOUNT;
    }

    // EFFECTS: returns where the pellet should start according to which direction the player is facing
    public double pelletXStart() {
        if (facingRight) {
            return playerX + (PLAYER_WIDTH / 2.0) + (Pellet.PELLET_WIDTH / 2) + 1;
        } else {
            return playerX - (PLAYER_WIDTH / 2.0) - (Pellet.PELLET_WIDTH / 2) - 1;
        }
    }

    // EFFECTS: returns 1 or -1 based on if the player is facing right or left respectively
    public int shootDirection() {
        if (facingRight) {
            return 1;
        } else {
            return -1;
        }
    }

    // MODIFIES: magazine
    // EFFECTS: if player has ammo, shoots a new pellet object
    public void shoot() {
        if (magazine > 0) {
            Pellet p = new Pellet(pelletXStart(), playerY, Pellet.SHOT_SPEED * shootDirection());
            pellets.addPellet(p);
            magazine--;
        } //
    }

    // MODIFIES: dy
    // EFFECTS: suddenly sets player dy to face upwards, simulating a jump
    public void jump() {
        if (playerY == World.SCENE_HEIGHT - PLAYER_HEIGHT / 2.0) { //change to also include platforms
            dy = JUMP_STRENGTH;
        }
    }

    // EFFECTS: returns true if the player has a pellet in them, false otherwise
    public boolean checkifGotShotBy(Player otherPlayer) {
        boolean colliding = false;
        boolean collidingx;
        boolean collidingy;

        double leftx = playerX - PLAYER_WIDTH / 2.0;
        double rightx = playerX + PLAYER_WIDTH / 2.0;

        double topy = playerY - PLAYER_HEIGHT / 2.0;
        double boty = playerY + PLAYER_HEIGHT / 2.0;

        for (Pellet p : otherPlayer.getPellets().getListOfPellets()) {
            collidingx = leftx <= p.getPelletX() && p.getPelletX() <= rightx;
            collidingy = topy <= p.getPelletY() && p.getPelletY() <= boty;
            colliding = collidingx && collidingy;

            if (colliding) {
                break;
            }
        }
        return colliding;
    }

}
