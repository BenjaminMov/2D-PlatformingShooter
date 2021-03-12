package ui.gui.Tools;

import ui.gui.EditorPanel;

import javax.swing.*;
import java.awt.event.MouseEvent;

public abstract class Tool {

    protected boolean active;
    protected EditorPanel editorPanel;
    protected JButton button;

    public Tool(EditorPanel editorPanel, JComponent parent) {
        this.editorPanel = editorPanel;
        createButton(parent);
        addToParent(parent);
        active = false;
        addListener();
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // EFFECTS: sets this Tool's active field to true
    public void activate() {
        active = true;
    }

    // EFFECTS: sets this Tool's active field to false
    public void deactivate() {
        active = false;
    }

    // EFFECTS: creates button to activate tool
    protected abstract void createButton(JComponent parent);

    // EFFECTS: adds a listener for this tool
    protected abstract void addListener();

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }

    // EFFECTS: default behaviour does nothing
    public void mousePressedInEditingArea(MouseEvent e) {}

    // EFFECTS: default behaviour does nothing
    public void mouseReleasedInEditingArea(MouseEvent e) {}

    // EFFECTS: default behaviour does nothing
    public void mouseClickedInEditingArea(MouseEvent e) {}

    // EFFECTS: default behaviour does nothing
    public void mouseDraggedInEditingArea(MouseEvent e) {}

}
