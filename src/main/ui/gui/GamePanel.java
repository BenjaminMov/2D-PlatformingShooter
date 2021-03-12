package ui.gui;

import exceptions.NoWinnerException;
import model.Pellet;
import model.Platform;
import model.Player;
import model.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {


    private static String over = "Game Over!";
    private static final String REPLAY = "Press R to play again";
    private static final String MENU = "Press M to return to menu";

    private static final Integer AMMO_PADDING = 5;
    private static final Integer X_PADDING = 3;

    private World world;

    public GamePanel(World world) {
        setPreferredSize(new Dimension(World.SCENE_WIDTH, World.SCENE_HEIGHT));
        this.world = world;
        setBackground(Color.GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (world.getIfGameOver()) {
            gameOver(g);
        }
    }

    // EFFECTS: if either of the players is no longer alive, return a string of the other player winning
    private String getWinner() throws NoWinnerException {
        String winningString = null;
        if (!world.getPlayer1().getAlive()) {
            winningString = "Player 2 Wins!";
        } else if (!world.getPlayer2().getAlive()) {
            winningString = "Player 1 Wins!";
        }
        if (winningString == null) {
            throw new NoWinnerException();
        } else {
            return winningString;
        }
    }

    private void gameOver(Graphics g) {
        try {
            String winStr = getWinner();
            Color saved = g.getColor();
            g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("Arial", 20, 20));
            FontMetrics fm = g.getFontMetrics();
            centreString(winStr, g, fm, World.SCENE_HEIGHT / 2);
            centreString(REPLAY, g, fm, World.SCENE_HEIGHT / 2 + 50);
            centreString(MENU, g, fm, World.SCENE_HEIGHT / 2 + 100);
            g.setColor(saved);
        } catch (NoWinnerException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position positionY
    private void centreString(String str, Graphics g, FontMetrics fm, int positionY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (World.SCENE_WIDTH - width) / 2, positionY);
    }

    private void drawGame(Graphics g) {
        Player p1 = world.getPlayer1();
        Player p2 = world.getPlayer2();

        drawPlayer(g, p1, World.P1_COLOUR);
        drawPlayer(g, p2, World.P2_COLOUR);

        drawPlayerPellets(g, p1);
        drawPlayerPellets(g, p2);

        drawPlatforms(g);
    }



    private void drawPlayer(Graphics g, Player p, Color color) {
        Color playerColour = g.getColor();
        g.setColor(color);
        g.fillRect(p.getPlayerX() - Player.PLAYER_WIDTH / 2, p.getPlayerY() - Player.PLAYER_HEIGHT / 2,
                   Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        g.setColor(playerColour);
        showAmmo(g, p);

    }


    private void showAmmo(Graphics g, Player p) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        FontMetrics fm = g.getFontMetrics();
        //centreString(p.getMagazine().toString(), g, fm, p.getPlayerY() - Player.PLAYER_HEIGHT / 2 - AMMO_PADDING);
        g.drawString(p.getMagazine().toString(), p.getPlayerX() - X_PADDING,
                p.getPlayerY() - Player.PLAYER_HEIGHT / 2 - AMMO_PADDING);
        g.setColor(saved);
    }


    private void drawPlatforms(Graphics g) {
        for (Platform p : world.getLevel().getPlatforms()) {
            drawPlatform(g, p);
        }
    }

    private void drawPlatform(Graphics g, Platform p) {
        Color colourP = g.getColor();
        g.setColor(World.PLATFORM_COLOUR);
        g.fillRect(p.getPlatformX() - p.getPlatformWidth() / 2, p.getPlatformY() - Platform.PLATFORM_HEIGHT,
                  p.getPlatformWidth(), Platform.PLATFORM_HEIGHT);
        g.setColor(colourP);
    }


    private void drawPlayerPellets(Graphics g, Player player) {
        for (Pellet p : player.getPellets().getListOfPellets()) {
            drawPlayerPellet(g, p);
        }
    }

    private void drawPlayerPellet(Graphics g, Pellet p) {
        g.setColor(World.PELLET_COLOUR);
        g.fillOval(p.getPelletX() - Pellet.PELLET_WIDTH / 2, p.getPelletY() - Pellet.PELLET_HEIGHT / 2,
                  Pellet.PELLET_WIDTH, Pellet.PELLET_HEIGHT);
    }
}
