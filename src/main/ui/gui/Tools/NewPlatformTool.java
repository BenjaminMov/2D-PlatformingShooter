package ui.gui.Tools;

import model.Platform;
import ui.gui.EditorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class NewPlatformTool extends Tool {

    private Point mousePoint;
    private Platform designPlatform;

    public NewPlatformTool(EditorPanel editorPanel, JComponent parent) {
        super(editorPanel, parent);
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("New Platform");
        button = customizeButton(button);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new NewPlatformToolClickHandler());
    }

    @Override
    public void mousePressedInEditingArea(MouseEvent e) {
        mousePoint = e.getPoint();
        designPlatform = new Platform(mousePoint.x, mousePoint.y, 0);
        editorPanel.getDesignLevel().addPlatform(designPlatform);
    }

    @Override
    public void mouseDraggedInEditingArea(MouseEvent e) {
        designPlatform.setPlatformWidth(2 * (e.getX() - mousePoint.x));
    }


    private class NewPlatformToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the new platform tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            editorPanel.setActiveTool(NewPlatformTool.this);
        }
    }
}
