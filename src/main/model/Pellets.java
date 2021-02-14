package model;

import java.util.ArrayList;

// a list of Pellet objects that are seen on screen
public class Pellets {
    private ArrayList<Pellet> pelletArrayList;

    public Pellets() {
        pelletArrayList = new ArrayList<>();
    }

    //EFFECTS: gets the i'th term in the pellet list
    public Pellet getElement(int i) {
        return pelletArrayList.get(i);
    }

    //EFFECTS: returns the pellet arraylist
    public ArrayList<Pellet> getListOfPellets() {
        return pelletArrayList;
    }

    //EFFECTS: adds a pellet to the list of pellets
    public void addPellet(Pellet pellet) {
        pelletArrayList.add(pellet);
    }

    //EFFECTS: checks if the list of pellets is empty
    public boolean isEmpty() {
        return pelletArrayList.isEmpty();
    }

    // MODIFIES: this
    // EFFECTS: moves all pellets in the list of pellets
    public void movePellets() {
        for (Pellet i : pelletArrayList) {
            i.move();
        }
    }

    // EFFECTS: returns the current x position of all pellets in list
    public ArrayList<Double> getAllPelletX() {
        ArrayList<Double> pelletPositions = new ArrayList<>();
        for (Pellet p : pelletArrayList) {
            pelletPositions.add(p.getPelletX());
        }
        return pelletPositions;
    }

    // EFFECTS: returns the current x position of all pellets in list
    public ArrayList<Double> getAllPelletY() {
        ArrayList<Double> pelletPositions = new ArrayList<>();
        for (Pellet p : pelletArrayList) {
            pelletPositions.add(p.getPelletY());
        }
        return pelletPositions;
    }
}
