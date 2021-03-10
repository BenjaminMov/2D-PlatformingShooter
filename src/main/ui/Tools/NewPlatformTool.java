package ui.Tools;

import model.Platform;
import ui.EditorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class NewPlatformTool extends Tool {

    private Point mousePoint;

    public NewPlatformTool(EditorPanel editorPanel, JComponent parent) {
        super(editorPanel, parent);
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton();
        button = customizeButton(button);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new PlatformToolClickHandler());
    }

    @Override
    public void mousePressedInEditingArea(MouseEvent e) {
        mousePoint = e.getPoint();
    }

    @Override
    public void mouseDraggedInEditingArea(MouseEvent e) {
        Platform designPlatform = new Platform(mousePoint.x, mousePoint.y, 0);
        designPlatform.setPlatformWidth(2 * (e.getX() - mousePoint.x));
        editorPanel.getDesignLevel().addPlatform(designPlatform);
    }

    private class PlatformToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the new platform tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            editorPanel.setActiveTool(NewPlatformTool.this);
        }
    }
}
