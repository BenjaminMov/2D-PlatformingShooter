package ui.gui.Tools;

import model.Platform;
import ui.gui.EditorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class DeletePlatformTool extends Tool {

    private EditorPanel designPanel;

    public DeletePlatformTool(EditorPanel editorPanel, JComponent parent) {
        super(editorPanel, parent);
        designPanel = editorPanel;
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Erase");
        button = customizeButton(button);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new DeletePlatformTool.DeletePlatformToolClickHandler());
    }

    @Override
    public void mousePressedInEditingArea(MouseEvent e) {
        deleteIfInRange(e.getPoint());
    }

    @Override
    public void mouseDraggedInEditingArea(MouseEvent e) {
        deleteIfInRange(e.getPoint());
    }

    private boolean inDeleteRange(Platform platform, Point mousePoint) {
        boolean inPlatformYRange = platform.getPlatformY() - Platform.PLATFORM_HEIGHT <= mousePoint.y
                && mousePoint.y <= platform.getPlatformY();
        boolean inPlatformXRange = platform.getPlatformX() - (platform.getPlatformWidth() / 2) <= mousePoint.x
                && mousePoint.x < platform.getPlatformX() + (platform.getPlatformWidth() / 2);

        return inPlatformYRange && inPlatformXRange;
    }

    private void deleteIfInRange(Point point) {
        designPanel.getDesignLevel().getPlatforms().removeIf(platform -> inDeleteRange(platform, point));
    }

    private class DeletePlatformToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the new platform tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            editorPanel.setActiveTool(DeletePlatformTool.this);
        }
    }
}
