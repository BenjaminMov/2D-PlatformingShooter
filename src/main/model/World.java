package model;

//import java.awt.event.KeyEvent;

//Model of the world where 2 players play the game
public class World {
    public static final int SCENE_WIDTH = 800;
    public static final int SCENE_HEIGHT = 600;
    public static final int SPEEDX = 10;
    public static final int P1_STARTX = SCENE_WIDTH / 4;
    public static final int P_STARTY = SCENE_HEIGHT - Player.PLAYER_HEIGHT / 2;
    public static final int P2_STARTX = 3 * SCENE_WIDTH / 4;
    private boolean gameDone = false;

    private Player player1;
    private Player player2;
    private Level level;

    public World() {
        setUp();
    }

    private void setUp() {
        player1 = new Player(P1_STARTX, P_STARTY);
        player2 = new Player(P2_STARTX, P_STARTY);
        level = new Level();
    }

    // MODIFIES: player1, player2, gameDone
    // EFFECTS: implements physics and boundaries for all players, also removes players if shot
    //          ends the game if player has been shot
    public void update() {
        player1.move();
        player2.move();
        player1.getPellets().movePellets();
        player2.getPellets().movePellets();
        player1.enforceWall();
        player2.enforceWall();

        level.makePlatformsSolid(player1);
        level.makePlatformsSolid(player2);
        removePlayerIfShot(player1);
        removePlayerIfShot(player2);
        checkIfGameEnd();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Boolean getIfGameOver() {
        return gameDone;
    }

    public void setIfGameOver(Boolean over) {
        gameDone = over;
    }

    /*
    private void player1Control(int keyInput) {
        if (keyInput == KeyEvent.VK_KP_LEFT || keyInput == KeyEvent.VK_LEFT) {
            player1.setDx(-SPEEDX);
            player1.setFacingRight(false);
        } else if (keyInput == KeyEvent.VK_KP_RIGHT || keyInput == KeyEvent.VK_RIGHT) {
            player1.setDx(SPEEDX);
            player1.setFacingRight(true);
        } else if (keyInput == KeyEvent.VK_KP_UP || keyInput == KeyEvent.VK_UP) {
            player1.jump();
        } else if (keyInput == KeyEvent.VK_NUMPAD4) {
            player1.reload();
        } else if (keyInput == KeyEvent.VK_NUMPAD5) {
            player1.shoot();
        }
    }

    private void player2Control(int keyInput) {
        if (keyInput == KeyEvent.VK_A) {
            player2.setDx(-SPEEDX);
            player2.setFacingRight(false);
        } else if (keyInput == KeyEvent.VK_D) {
            player2.setDx(SPEEDX);
            player2.setFacingRight(true);
        } else if (keyInput == KeyEvent.VK_W) {
            player2.jump();
        } else if (keyInput == KeyEvent.VK_J) {
            player2.reload();
        } else if (keyInput == KeyEvent.VK_H) {
            player2.shoot();
        }
    }
     */

    // MODIFIES: player
    // EFFECTS: if player has been shot by themselves or the other player, set 'alive' to false
    private void removePlayerIfShot(Player player) {
        if (player.checkifGotShotBy(player1) || player.checkifGotShotBy(player2)) {
            player.setAlive(false);
        }
    }


    // MODIFIES: gameDone
    // EFFECTS: if any player is not alive, set gameDone to true
    private void checkIfGameEnd() {
        if (!player1.getAlive() || !player2.getAlive()) {
            gameDone = true;
        }
    }
}
