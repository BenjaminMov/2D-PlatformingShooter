package ui;

import exceptions.NoWinnerException;
import model.Pellet;
import model.Platform;
import model.Player;
import model.World;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static String over = "Game Over!";
    private static final String REPLAY = "";

    private static final Integer AMMO_PADDING = 5;
    private static final Integer X_PADDING = 3;

    private World world;

    public GamePanel(World world) {

        setPreferredSize(new Dimension(World.SCENE_WIDTH, World.SCENE_HEIGHT));
        setBackground(Color.GRAY);
        this.world = world;
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
        drawPlayer1(g);
        drawPlayer2(g);
        drawPlatforms(g);
        drawP1Pellets(g);
        drawP2Pellets(g);
        showAmmo(g, world.getPlayer1());
        showAmmo(g, world.getPlayer2());
    }


    private void drawPlayer1(Graphics g) {
        Player p = world.getPlayer1();
        Color p1Colour = g.getColor();
        g.setColor(World.P1_COLOUR);
        g.fillRect(p.getPlayerX() - Player.PLAYER_WIDTH / 2, p.getPlayerY() - Player.PLAYER_HEIGHT / 2,
                   Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        g.setColor(p1Colour);
    }

    private void drawPlayer2(Graphics g) {
        Player p = world.getPlayer2();
        Color p2Colour = g.getColor();
        g.setColor(World.P2_COLOUR);
        g.fillRect(p.getPlayerX() - Player.PLAYER_WIDTH / 2, p.getPlayerY() - Player.PLAYER_HEIGHT / 2,
                Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        g.setColor(p2Colour);
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

    private void drawP1Pellets(Graphics g) {
        for (Pellet p : world.getPlayer1().getPellets().getListOfPellets()) {
            drawP1Pellet(g, p);
        }
    }

    private void drawP1Pellet(Graphics g, Pellet p) {
        Color pelletColour = g.getColor();
        g.setColor(World.PELLET_COLOUR);
        g.fillOval(p.getPelletX() - Pellet.PELLET_WIDTH / 2, p.getPelletY() - Pellet.PELLET_HEIGHT / 2,
                  Pellet.PELLET_WIDTH, Pellet.PELLET_HEIGHT);
    }

    private void drawP2Pellets(Graphics g) {
        for (Pellet p : world.getPlayer2().getPellets().getListOfPellets()) {
            drawP2Pellet(g, p);
        }
    }

    private void drawP2Pellet(Graphics g, Pellet p) {
        Color pelletColour = g.getColor();
        g.setColor(World.PELLET_COLOUR);
        g.fillOval(p.getPelletX() - Pellet.PELLET_WIDTH / 2, p.getPelletY() - Pellet.PELLET_HEIGHT / 2,
                Pellet.PELLET_WIDTH, Pellet.PELLET_HEIGHT);
    }


}
