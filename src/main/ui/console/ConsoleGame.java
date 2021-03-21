package ui.console;

import exceptions.NoRemainingPlatformException;
import exceptions.NoSuchLevelNameException;
import exceptions.NoWinnerException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

//Class that represents a console based game
public class ConsoleGame {

    private static final String JSON_STORE = "./data/LevelCache.json";
    private Scanner input;
    private World world;
    private LevelBank levelBank;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    
    public ConsoleGame() {
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: if the game is not over, process the commands given by the user,
    //          if the game is over, return a string of the winner
    private void runGame() {
        String command;
        initialize();
        processMenuCommand();
        if (!world.getIfGameOver()) {
            displayControls();
        }

        while (!(world.getIfGameOver())) {
            giveLocInfo();

            command = input.next();
            command = command.toLowerCase();

            processCommand1(command);
            processCommand2(command);
            processCommand3(command);
            world.update();
        }

        String winner = null;
        try {
            winner = getWinner();
            System.out.println(winner);
        } catch (NoWinnerException e) {
            System.out.println("thanks for playing!");
        }
    }


    //EFFECTS: Processes menu command
    private void processMenuCommand() {
        System.out.println("E: level editor, L: choose level, Q: quit");

        String command;
        command = input.next();
        command = command.toLowerCase();

        switch (command) {
            case "e":
                startLevelEditor();
                break;
            case "l":
                chooseLevel();
                break;
            case "q":
                world.setIfGameOver(true);
                break;
        }
    }

    //MODIFIES: levelBank
    //EFFECTS: displays the possible levels and loads the chosen one
    private void chooseLevel() {

        System.out.println("Level Select:");
        printLevels();

        String command;
        command = input.next();
        command = command.toLowerCase();

        loadLevel(command);
    }

    //EFFECTS: gets the names of all levels and prints them
    private void printLevels() {
        List<Level> levels = levelBank.getAllLevels();

        for (Level lvl : levels) {
            System.out.println(lvl.getLevelName());
        }
    }

    //EFFECTS: Starts the level editor
    private void startLevelEditor() {
        System.out.println("enter the name of your new level");

        String command;
        command = input.next();
        command = command.toLowerCase();

        Level designLevel = new Level(command);

        Boolean inDesign = true;

        while (inDesign) {

            if (command.equals("q")) {
                inDesign = false;
            } else {
                try {
                    processDesignCommand(command, designLevel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //EFFECTS: processes a command in design menu
    private void processDesignCommand(String name, Level designLevel) throws IOException {
        displayDesignMenu();

        String command;
        command = input.next();
        command = command.toLowerCase();


        switch (command) {
            case "np":
                processNewPlatform(designLevel);
                break;
            case "dp":
                removeLastPlatform(designLevel);
                break;
            case "s":
                saveLevel(designLevel);
                break;
            case "q":
                runGame();
        }
    }

    //EFFECTS: removes the last added platform
    private void removeLastPlatform(Level designLevel) {
        try {
            designLevel.removeLastPlatform();
        } catch (NoRemainingPlatformException e) {
            e.printStackTrace();
            System.out.println("no platforms to remove");
        }
    }

    //EFFECTS: takes in a command and makes a new platform accordingly
    private void processNewPlatform(Level level) throws IOException {
        System.out.println("specify x y width as double with spaces in between");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String lines = br.readLine();
        Platform platform;

        String[] strList = lines.trim().split(" ");
        Integer x = Integer.parseInt(strList[0]);
        Integer y = Integer.parseInt(strList[1]);
        Integer width = Integer.parseInt(strList[2]);

        platform = new Platform(x, y, width);

        level.addPlatform(platform);
        System.out.println("successfully added platform at " + x + ", " + y + ", with width: " + width);
    }

    //EFFECTS: displays design menu
    private void displayDesignMenu() {
        System.out.println("Use the command 'NP' to make a new platform");
        System.out.println("Use the command 'DP' to delete that last platform");
        System.out.println("Use the command 'S' to save project");
        System.out.println("Use the command 'Q' to leave editor menu");
    }


    // EFFECTS: outputs the location/velocity/magazine/pellet information of each player
    private void giveLocInfo() {
        System.out.println("Player1:");
        System.out.println("\t x: " + world.getPlayer1().getPlayerX()
                         + "\t y: " + world.getPlayer1().getPlayerY());
        System.out.println("\t dx: " + world.getPlayer1().getDx()
                         + "\t dy " + world.getPlayer1().getDy());
        System.out.println("\t facingRight? " + world.getPlayer1().isFacingRight());
        System.out.println("\t onScreenPellets " + world.getPlayer1().getPellets().getAllPelletX());

        System.out.println("Player2:");
        System.out.println("\t x: " + world.getPlayer2().getPlayerX()
                         + "\t y: " + world.getPlayer2().getPlayerY());
        System.out.println("\t dx: " + world.getPlayer2().getDx()
                         + "\t dy " + world.getPlayer2().getDy());
        System.out.println("\t facingRight? " + world.getPlayer2().isFacingRight());
        System.out.println("\t onScreenPellets " + world.getPlayer2().getPellets().getAllPelletX());
    }

    // EFFECTS: sets changes player1 information based on input
    private void processCommand1(String command) {
        switch (command) {
            case "w":
                world.getPlayer1().jump();
                break;
            case "a":
                world.getPlayer1().setDx(-World.SPEEDX);
                world.getPlayer1().setFacingRight(false);
                break;
            case "d":
                world.getPlayer1().setDx(World.SPEEDX);
                world.getPlayer1().setFacingRight(true);
                break;
        }
        if (command.equals("j")) {
            world.getPlayer1().reload();
            System.out.println("Player1 has " + world.getPlayer1().getMagazine().toString() + " pellets remaining");
        } else if (command.equals("k")) {
            world.getPlayer1().shoot();
            printAfterShoot(world.getPlayer1());
            System.out.println("Player1 has " + world.getPlayer1().getMagazine().toString()
                    + " pellets remaining");
        }
    }

    //EFFECTS: changes player2 information based on inputs
    private void processCommand2(String command) {
        switch (command) {
            case "8":
                world.getPlayer2().jump();
                break;
            case "4":
                world.getPlayer2().setDx(-World.SPEEDX);
                world.getPlayer2().setFacingRight(false);
                break;
            case "6":
                world.getPlayer2().setDx(World.SPEEDX);
                world.getPlayer2().setFacingRight(true);
                break;
        }
        if (command.equals("1")) {
            world.getPlayer2().reload();
            System.out.println("Player2 has " + world.getPlayer2().getMagazine().toString()
                    + " pellets remaining");
        } else if (command.equals("2")) {
            world.getPlayer2().shoot();
            printAfterShoot(world.getPlayer2());
            System.out.println("Player2 has " + world.getPlayer2().getMagazine().toString()
                    + " pellets remaining");
        }
    }

    //EFFECTS: processes a command
    private void processCommand3(String command) {
        switch (command) {
            case "showp":
                int platNum = 1;
                for (Platform p : world.getLevel().getPlatforms()) {
                    System.out.println(platNum
                            + ": x: " + p.getPlatformX()
                            + " y: " + p.getPlatformY()
                            + " width: " + p.getPlatformWidth());
                    platNum++;
                }
                break;
            case "q":
                world.setIfGameOver(true);
                break;
            default:
                break;
        }
    }

    // EFFECTS: Prints a corresponding statement to show player whether or not they shot a pellet
    private void printAfterShoot(Player player) {
        if (player.getMagazine() > 0) {
            System.out.println("BANG!");
        } else {
            System.out.println("no ammo!");
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

    // EFFECTS: display the controls of the two players for the user
    private void displayControls() {
        System.out.println("Player1: WASD to move");
        System.out.println("\t J to reload");
        System.out.println("\t K to shoot");

        System.out.println("Player2: (numpad) 8456 to move");
        System.out.println("\t num1 to reload");
        System.out.println("\t num2 to shoot");

        System.out.println("Press 'Q' to quit");
    }

    // EFFECTS: instantiates a new world, also instantiates a new scanner to get user input information
    private void initialize() {
        world = new World();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        levelBank = new LevelBank();

        try {
            levelBank = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: saves the level to file
    private void saveLevel(Level level) {
        try {
            jsonWriter.open();
            levelBank.addLevel(level);
            jsonWriter.write(levelBank);
            jsonWriter.close();
            System.out.println("Saved " + level.getLevelName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
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
}

