package ui;

import model.World;

import java.util.Scanner;

public class ConsoleGame {

    private Scanner input;
    private World world;
    
    public ConsoleGame() {
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: if the game is not over, process the commands given by the user,
    //          if the game is over, return a string of the winner
    private void runGame() {
        String command;
        initialize();
        displayControls();

        while (!(world.getIfGameOver())) {
            giveLocInfo();

            command = input.next();
            command = command.toLowerCase();

            processCommand1(command);
            processCommand2(command);
            world.update();
        }

        String winner = getWinner();
        System.out.println(winner);
    }

    // EFFECTS: outputs the location/velocity/magazine/pellet information of each player
    private void giveLocInfo() {
        System.out.println("Player1:");
        System.out.println("\t x: " + world.getPlayer1().getPlayerX()
                         + "\t y: " + world.getPlayer1().getPlayerY());
        System.out.println("\t dx: " + world.getPlayer1().getDx()
                         + "\t dy " + world.getPlayer1().getDy());
        System.out.println("\t facingRight? " + world.getPlayer1().getFacingRight());
        System.out.println("\t onScreenPellets " + world.getPlayer1().getPellets().getAllPelletX());

        System.out.println("Player2:");
        System.out.println("\t x: " + world.getPlayer2().getPlayerX()
                         + "\t y: " + world.getPlayer2().getPlayerY());
        System.out.println("\t dx: " + world.getPlayer2().getDx()
                         + "\t dy " + world.getPlayer2().getDy());
        System.out.println("\t facingRight? " + world.getPlayer2().getFacingRight());
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
            System.out.println("Player1 has " + world.getPlayer1().getMagazine().toString()
                               + " pellets remaining");
        } else if (command.equals("k")) {
            world.getPlayer1().shoot();
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
            System.out.println("Player2 has " + world.getPlayer2().getMagazine().toString()
                    + " pellets remaining");
        }
    }

    // EFFECTS: if either of the players is no longer alive, return a string of the other player winning
    private String getWinner() {
        String winningString = "";
        if (!world.getPlayer1().getAlive()) {
            winningString = "Player 2 Wins!";
        } else if (!world.getPlayer2().getAlive()) {
            winningString = "Player 1 Wins!";
        }
        return winningString;
    }

    // EFFECTS: display the controls of the two players for the user
    private void displayControls() {
        System.out.println("Player1: WASD to move");
        System.out.println("\t J to reload");
        System.out.println("\t K to shoot");

        System.out.println("Player2: (numpad) 8456 to move");
        System.out.println("\t num1 to reload");
        System.out.println("\t num2 to shoot");
    }

    // EFFECTS: instantiates a new world, also instantiates a new scanner to get user input information
    private void initialize() {
        world = new World();
        input = new Scanner(System.in);
    }

}

