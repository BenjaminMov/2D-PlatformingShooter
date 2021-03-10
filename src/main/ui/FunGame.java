package ui;

import exceptions.NoSuchLevelNameException;
import model.Level;
import model.LevelBank;
import model.World;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class FunGame extends JFrame {

    private static final int INTERVAL = 10;
    public static final String JSON_STORE = "./data/LevelCache.json";

    private World world;
    private LevelBank levelBank;
    private GamePanel gp;
    private EditorPanel ep;
    private ArrayList<String> availableLevels;

    private JsonReader jsonReader;

    // EFFECTS: sets up window in which Fun Game world will be played
    public FunGame() {
        super("Fun Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        initialize();

        String[] options = {"Play", "New Level", "Quit"};

        int choice = JOptionPane.showOptionDialog(null, "Fun Game",
                "Welcome!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        processChoice(choice);
    }

    //MODIFIES: this
    //EFFECTS: presents a new panel depending on choice of user
    private void processChoice(int choice) {
        if (choice == 0) {
            chooseLevel();
            add(gp);
            addKeyListener(new KeyHandler());
            pack();
            centreOnScreen();
            setVisible(true);
            addTimer();
        } else if (choice == 1) {
            askLevelName();
            add(ep);
            addKeyListener(new KeyHandler());
            pack();
            centreOnScreen();
            setVisible(true);
            addTimer(); //for animation
        } else {
            this.dispose();
        }
    }

    private void askLevelName() {

        String newLvlName = (String)JOptionPane.showInputDialog(null, "Choose your Level",
                "Level Select", JOptionPane.QUESTION_MESSAGE, null, null, null);


    }

    private void chooseLevel() {

        String[] available = availableLevels.toArray(new String[0]);

        String chosenLvl = (String)JOptionPane.showInputDialog(null, "Choose your Level",
                "Level Select", JOptionPane.QUESTION_MESSAGE, null, available, available[0]);

        loadLevel(chosenLvl);
    }

    // EFFECTS: instantiates a new world and sets up storages
    private void initialize() {
        world = new World();
        gp = new GamePanel(world);
        ep = new EditorPanel(this);
        jsonReader = new JsonReader(JSON_STORE);
        availableLevels = new ArrayList<>();

        try {
            levelBank = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Level lvl : levelBank.getAllLevels()) {
            availableLevels.add(lvl.getLevelName());
        }

    }

    public LevelBank getLevelBank() {
        return levelBank;
    }

    // MODIFIES: none
    // EFFECTS:  initializes a timer that updates world each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, ae -> {
            world.update();
            gp.repaint();
        });

        t.start();
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            world.keyPressed(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            world.keyReleased(e.getKeyCode());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Level from file
    private void loadLevel(String levelName) {
        try {
            levelBank = jsonReader.read();
            Level loadedLevel = null;
            try {
                loadedLevel = levelBank.findLevel(levelName);
                world.setLevel(loadedLevel);
            } catch (NoSuchLevelNameException e) {
                e.printStackTrace();
            }
            System.out.println("Loaded " + loadedLevel.getLevelName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Play the world
    public static void main(String[] args) {
        new FunGame();
    }
}
