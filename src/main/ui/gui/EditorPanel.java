package ui.gui;

import model.Level;
import model.LevelBank;
import model.Platform;
import model.World;
import persistence.JsonWriter;
import ui.gui.Tools.DeletePlatformTool;
import ui.gui.Tools.NewPlatformTool;
import ui.gui.Tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static ui.gui.FunGame.JSON_STORE;

public class EditorPanel extends JPanel {

    private Level designLevel;
    private List<Tool> tools;
    private Tool activeTool;
    private JButton saveButton;
    private JButton quitButton;

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
        makeQuitButton(funGame);
    }

    private void makeQuitButton(FunGame funGame) {
        quitButton = new JButton("Back To Menu");
        this.add(quitButton);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funGame.dispose();
                new FunGame();
            }
        });
    }

    private void makeSaveButton(FunGame funGame) {
        saveButton = new JButton("SAVE");
        this.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLevel(designLevel, funGame.getLevelBank());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlatforms(g);
    }

    private void drawPlatforms(Graphics g) {
        for (Platform p : designLevel.getPlatforms()) {
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

    // EFFECTS: if activeTool != null, then mouseReleasedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMouseReleased(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseReleasedInEditingArea(e);
        }
        repaint();
    }

    public Level getDesignLevel() {
        return designLevel;
    }

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
            handleMouseReleased(translateEvent(e));
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
