package ui.gui;

import model.Level;
import model.LevelBank;
import model.Platform;
import model.World;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static ui.gui.FunGame.JSON_STORE;

// A panel for editing levels
public class EditorPanel extends JPanel {

    private Level designLevel;
    private List<Tool> tools;
    private Tool activeTool;

    private JsonWriter jsonWriter;

    public EditorPanel(FunGame funGame) {
        designLevel = new Level("No Name");
        jsonWriter = new JsonWriter(JSON_STORE);
        tools = new ArrayList<>();

        setPreferredSize(new Dimension(World.SCENE_WIDTH, World.SCENE_HEIGHT));
        setBackground(Color.GRAY);
        setLayout(new FlowLayout(FlowLayout.LEADING));
        createTools();
        initializeInteraction();
        makeSaveButton(funGame);
        makeDeleteButton(funGame);
        makeQuitButton(funGame);
    }

    // EFFECTS: makes a delete button
    private void makeDeleteButton(FunGame funGame) {
        JButton deleteButton = new JButton("DELETE LEVEL");
        this.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLevel(designLevel, funGame.getLevelBank());
                funGame.dispose();
                new FunGame();
            }
        });
    }

    // EFFECTS: makes a quit button
    private void makeQuitButton(FunGame funGame) {
        JButton quitButton = new JButton("MENU");
        this.add(quitButton);

        quitButton.addActionListener(e -> {
            funGame.dispose();
            new FunGame();
        });
    }

    // EFFECTS: makes a save button
    private void makeSaveButton(FunGame funGame) {
        JButton saveButton = new JButton("SAVE");
        this.add(saveButton);

        saveButton.addActionListener(e -> saveLevel(designLevel, funGame.getLevelBank()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlatforms(g);
    }

    // draws all platforms in the level
    private void drawPlatforms(Graphics g) {
        for (Platform p : designLevel.getPlatforms()) {
            drawPlatform(g, p);
        }
    }

    // EFFECTS: draws a platform for a given platform
    private void drawPlatform(Graphics g, Platform p) {
        Color colourP = g.getColor();
        g.setColor(World.PLATFORM_COLOUR);
        g.fillRect(p.getPlatformX() - p.getPlatformWidth() / 2, p.getPlatformY() - Platform.PLATFORM_HEIGHT,
                p.getPlatformWidth(), Platform.PLATFORM_HEIGHT);
        g.setColor(colourP);
    }

    // EFFECTS: if activeTool != null, then mousePressedInEditingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMousePressed(MouseEvent e)  {
        if (activeTool != null) {
            activeTool.mousePressedInEditingArea(e);
        }
        repaint();
    }

    // EFFECTS: if activeTool != null, then mouseDraggedInEditingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMouseDragged(MouseEvent e)  {
        if (activeTool != null) {
            activeTool.mouseDraggedInEditingArea(e);
        }
        repaint();
    }

    // getters
    public Level getDesignLevel() {
        return designLevel;
    }

    //setters
    public void setDesignLevel(Level designLevel) {
        this.designLevel = designLevel;
    }

    // EFFECTS: translates the mouse event to current drawing's coordinate system
    private MouseEvent translateEvent(MouseEvent e) {
        return SwingUtilities.convertMouseEvent(e.getComponent(), e, this);
    }

    // MODIFIES: this
    // EFFECTS:  sets the given tool as the activeTool
    public void setActiveTool(Tool toolActive) {
        if (activeTool != null) {
            activeTool.deactivate();
        }
        toolActive.activate();
        activeTool = toolActive;
    }

    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all tools
    private void createTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0,1));
        toolArea.setSize(new Dimension(0, 0));
        add(toolArea, BorderLayout.SOUTH);

        NewPlatformTool npt = new NewPlatformTool(this, toolArea);
        tools.add(npt);

        DeletePlatformTool dpt = new DeletePlatformTool(this, toolArea);
        tools.add(dpt);

        setActiveTool(npt);
    }

    // MODIFIES: this
    // EFFECTS:  initializes a DrawingMouseListener to be used in the JFrame
    private void initializeInteraction() {
        EditorMouseListener eml = new EditorMouseListener();
        addMouseListener(eml);
        addMouseMotionListener(eml);
    }

    // EFFECTS: saves the level to file
    private void saveLevel(Level level, LevelBank levelBank) {
        try {
            jsonWriter.open();
            levelBank.getAllLevels().removeIf(lvl -> level.getLevelName().equals(lvl.getLevelName()));
            levelBank.addLevel(level);
            jsonWriter.write(levelBank);
            jsonWriter.close();
            System.out.println("Saved " + level.getLevelName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: deletes the level that is currently being edited
    private void deleteLevel(Level level, LevelBank levelBank) {
        try {
            jsonWriter.open();
            levelBank.getAllLevels().remove(level);
            jsonWriter.write(levelBank);
            jsonWriter.close();
            System.out.println("Deleted " + level.getLevelName() + " from " + JSON_STORE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setLevelName(String newLvlName) {
        designLevel.setLevelName(newLvlName);
    }

    private class EditorMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            handleMousePressed(translateEvent(e));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            handleMouseDragged(translateEvent(e));
        }
    }

}

