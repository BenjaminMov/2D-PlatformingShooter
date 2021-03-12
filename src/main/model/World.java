package model;

//import java.awt.event.KeyEvent;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

//Model of the world where 2 players play the game
public class World extends Timer {
    public static final int SCENE_WIDTH = 800;
    public static final int SCENE_HEIGHT = 600;
    public static final int SPEEDX = 10;
    public static final int P1_STARTX = SCENE_WIDTH / 4;
    public static final int P_STARTY = SCENE_HEIGHT - Player.PLAYER_HEIGHT / 2;
    public static final int P2_STARTX = 3 * SCENE_WIDTH / 4;
    private boolean gameDone = false;

    public static final double RELOAD_DELAY = 1000;

    private Player player1;
    private Player player2;
    private Level level;

    public static final Color P1_COLOUR = new Color(250,50,50);
    public static final Color P2_COLOUR = new Color(50,200,250);
    public static final Color PLATFORM_COLOUR = new Color(0,0,0);
    public static final Color PELLET_COLOUR = new Color(250,190,190);


    public World() {
        setUp();
    }

    private void setUp() {
        player1 = new Player(P1_STARTX, P_STARTY);
        player2 = new Player(P2_STARTX, P_STARTY);
        level = new Level("LevelAlpha");
    }

    private void cleanUp() {
        gameDone = false;
        player1 = new Player(P1_STARTX, P_STARTY);
        player2 = new Player(P2_STARTX, P_STARTY);
        player1.setMagazine(0);
        player2.setMagazine(0);
    }

    // MODIFIES: player1, player2, gameDone
    // EFFECTS: implements physics and boundaries for all players, also removes players if shot
    //          ends the game if player has been shot
    public void update() {
        level.makePlatformsSolid(player1);
        level.makePlatformsSolid(player2);

        player1.move();
        player2.move();
        player1.getPellets().movePellets();
        player2.getPellets().movePellets();
        player1.enforceWall();
        player2.enforceWall();

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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Boolean getIfGameOver() {
        return gameDone;
    }

    public void setIfGameOver(Boolean over) {
        gameDone = over;
    }

    /*

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

    public void reloadWithDelay(Player player) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                player.reload();
                player.setCanMove(true);
            }
        };
        player.setCanMove(false);
        schedule(timerTask, (long) RELOAD_DELAY);
    }

    public void keyPressed(int keyInput) {
        if (player1.isCanMove()) {
            if (keyInput == KeyEvent.VK_LEFT) {
                player1.setDx(-SPEEDX);
                player1.setFacingRight(false);
            } else if (keyInput == KeyEvent.VK_RIGHT) {
                player1.setDx(SPEEDX);
                player1.setFacingRight(true);
            } else if (keyInput == KeyEvent.VK_UP) {
                player1.jump();
            } else if (keyInput == KeyEvent.VK_NUMPAD4) {
                reloadWithDelay(player1);
            } else if (keyInput == KeyEvent.VK_NUMPAD5) {
                player1.shoot();
            }
        }

        if (player2.isCanMove()) {
            if (keyInput == KeyEvent.VK_A) {
                player2.setDx(-SPEEDX);
                player2.setFacingRight(false);
            } else if (keyInput == KeyEvent.VK_D) {
                player2.setDx(SPEEDX);
                player2.setFacingRight(true);
            } else if (keyInput == KeyEvent.VK_W) {
                player2.jump();
            } else if (keyInput == KeyEvent.VK_J) {
                reloadWithDelay(player2);
            } else if (keyInput == KeyEvent.VK_K) {
                player2.shoot();
            }

            if (keyInput == KeyEvent.VK_R && gameDone) {
                cleanUp();
            }
        }
    }


    public void keyReleased(int keyInput) {
        if (keyInput == KeyEvent.VK_LEFT || keyInput == KeyEvent.VK_RIGHT) {
            player1.setDx(0);
        }

        if (keyInput == KeyEvent.VK_A || keyInput == KeyEvent.VK_D) {
            player2.setDx(0);
        }
    }
}
