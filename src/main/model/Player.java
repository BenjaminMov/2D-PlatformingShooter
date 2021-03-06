package model;

// Model of a player than can be controlled by the user
public class Player {
    private int playerX;
    private int playerY;
    private int dx;
    private int dy;
    private int gravity;
    private Pellets pellets;

    private int magazine = 0;
    private boolean facingRight;
    private boolean onPlatform;
    private boolean alive = true;

    private boolean canMove = true;

    public static final int PLAYER_WIDTH = 16;
    public static final int PLAYER_HEIGHT = 20;
    public static final int RELOAD_AMOUNT = 3;
    public static final int JUMP_STRENGTH = -19;

    public static final int NORMAL_GRAVITY = 1;


    public Player(int x, int y) {
        this.playerX = x;
        this.playerY = y;
        dx = 0;
        dy = 0;
        pellets = new Pellets();
        gravity = NORMAL_GRAVITY;
    }

    //getters
    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getGravity() {
        return gravity;
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

    public boolean isFacingRight() {
        return facingRight;
    }

    public boolean isCanMove() {
        return canMove;
    }

    //setters
    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setGravity(int gravity) {
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

    public void setMagazine(int magazine) {
        this.magazine = magazine;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    //MODIFIES: playerX, playerY, dy
    //EFFECTS: gives the next position of the player
    public void move() {
        if (playerY <= World.P_STARTY && !onPlatform) {
            dy = dy + gravity;
        }
        playerX = playerX + dx;
        playerY = playerY + dy;
    }

    //MODIFIES: playerX, playerY
    //EFFECTS: Ensures the player is unable to leave the bounds of the screen
    public void enforceWall() {
        if (playerX - PLAYER_WIDTH / 2 + dx < 0) {
            playerX = PLAYER_WIDTH / 2;
            dx = 0;
        } else if (playerX + PLAYER_WIDTH / 2 + dx > World.SCENE_WIDTH) {
            playerX = World.SCENE_WIDTH - PLAYER_WIDTH / 2;
            dx = 0;
        }
        if (playerY - PLAYER_HEIGHT / 2 + dy < 0) {
            playerY = PLAYER_HEIGHT / 2;
            dy = 0;
        } else if (playerY + PLAYER_HEIGHT / 2 + dy > World.SCENE_HEIGHT) {
            playerY = World.SCENE_HEIGHT - PLAYER_HEIGHT / 2;
            dy = 0;
        }
    }

    // MODIFIES: magazine
    // EFFECTS: adds the constant RELOAD_AMOUNT to magazine field
    public void reload() {
        magazine += RELOAD_AMOUNT;
    }

    // EFFECTS: returns where the pellet should start according to which direction the player is facing
    public int pelletXStart() {
        if (facingRight) {
            return playerX + (PLAYER_WIDTH / 2) + (Pellet.PELLET_WIDTH / 2) + 1;
        } else {
            return playerX - (PLAYER_WIDTH / 2) - (Pellet.PELLET_WIDTH / 2) - 1;
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
        if (playerY == World.SCENE_HEIGHT - PLAYER_HEIGHT / 2 || onPlatform) {
            dy = JUMP_STRENGTH;
        }
    }

    // EFFECTS: returns true if the player has a pellet in them, false otherwise
    public boolean checkifGotShotBy(Player otherPlayer) {
        boolean colliding = false;
        boolean collidingx;
        boolean collidingy;

        double leftx = playerX - PLAYER_WIDTH / 2;
        double rightx = playerX + PLAYER_WIDTH / 2;

        double topy = playerY - PLAYER_HEIGHT / 2;
        double boty = playerY + PLAYER_HEIGHT / 2;


        for (Pellet p : otherPlayer.getPellets().getListOfPellets()) {

            boolean goingThroughLeft = (p.getPelletX() <= rightx) && (p.getPelletX() + p.getDx() >= leftx);
            boolean goingThroughRight = (p.getPelletX() >= leftx) && (p.getPelletX() + p.getDx() <= rightx);

            collidingx = goingThroughLeft || goingThroughRight;
            collidingy = topy <= p.getPelletY() && p.getPelletY() <= boty;
            colliding = collidingx && collidingy;

            if (colliding) {
                break;
            }
        }
        return colliding;
    }


}
