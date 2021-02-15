package model;

import java.util.ArrayList;

// a list of Pellet objects that are seen on screen
public class Pellets {

    private ArrayList<Pellet> pellets;

    public Pellets() {
        pellets = new ArrayList<>();
    }

    //EFFECTS: gets the i'th term in the pellet list
    public Pellet getElement(int i) {
        return pellets.get(i);
    }

    //EFFECTS: returns the pellet arraylist
    public ArrayList<Pellet> getListOfPellets() {
        return pellets;
    }

    //EFFECTS: adds a pellet to the list of pellets
    public void addPellet(Pellet pellet) {
        pellets.add(pellet);
    }

    //EFFECTS: checks if the list of pellets is empty
    public boolean isEmpty() {
        return pellets.isEmpty();
    }

    //EFFECTS: gets number of pellets that belong to a player
    public int size() {
        return pellets.size();
    }

    // MODIFIES: this
    // EFFECTS: moves all pellets in the list of pellets
    public void movePellets() {
        for (Pellet i : pellets) {
            i.move();
        }
    }

    // EFFECTS: returns the current x position of all pellets in list
    public ArrayList<Double> getAllPelletX() {
        ArrayList<Double> pelletPositions = new ArrayList<>();
        for (Pellet p : pellets) {
            pelletPositions.add(p.getPelletX());
        }
        return pelletPositions;
    }

    // EFFECTS: returns the current x position of all pellets in list
    public ArrayList<Double> getAllPelletY() {
        ArrayList<Double> pelletPositions = new ArrayList<>();
        for (Pellet p : pellets) {
            pelletPositions.add(p.getPelletY());
        }
        return pelletPositions;
    }
}
