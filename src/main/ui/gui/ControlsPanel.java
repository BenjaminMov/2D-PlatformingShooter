package ui.gui;

import model.World;

import javax.swing.*;
import java.awt.*;

// A panel to display the controls
public class ControlsPanel extends JPanel {

    private static final int BUTTON_X_PADDING = 10;
    private static final int BUTTON_Y_PADDING = 10;

    private static final int P1_CONTROLS_PADDING_X = 150;
    private static final int P1_CONTROLS_PADDING_Y = 150;
    private static final int P2_CONTROLS_PADDING_X = P1_CONTROLS_PADDING_X + 250;

    public ControlsPanel(FunGame funGame) {
        setPreferredSize(new Dimension(World.SCENE_WIDTH, World.SCENE_HEIGHT));
        setBackground(Color.lightGray);
        setLayout(null);
        makeQuitButton(funGame);
        displayP1Controls();
        displayP2Controls();
    }

    // EFFECTS: displays the Player 1 controls
    private void displayP1Controls() {
        String text = "PLAYER 1 CONTROLS " + "<br />" + "<br />"
                + "W : Jump" + "<br />"
                + "A : Move Left" + "<br />"
                + "D : Move Right" + "<br />"
                + "J : Reload" + "<br />"
                + "K : Shoot";

        initializeText(text, P1_CONTROLS_PADDING_X, P1_CONTROLS_PADDING_Y);
    }

    // EFFECTS: displays the Player 2 controls
    private void displayP2Controls() {
        String text = "PLAYER 2 CONTROLS " + "<br />" + "<br />"
                + "Up Arrow : Jump" + "<br />"
                + "Left Arrow : Move Left" + "<br />"
                + "Right Arrow : Move Right" + "<br />"
                + "numPad 4 OR 9 : Reload" + "<br />"
                + "numPad 5 OR 0 : Shoot";

        initializeText(text, P2_CONTROLS_PADDING_X, P1_CONTROLS_PADDING_Y);
    }

    // EFFECTS: displays given text with formatting
    private void initializeText(String text, int x, int y) {
        JLabel controls = new JLabel("<html>" + text + "</html>");
        controls.setFont(new Font("Arial", Font.BOLD, 20));
        Dimension size = controls.getPreferredSize();
        controls.setBounds(x, y, size.width, size.height);
        this.add(controls);
    }

    // EFFECTS: makes a quit button
    private void makeQuitButton(FunGame funGame) {
        JButton quitButton = new JButton("MENU");
        Dimension size = quitButton.getPreferredSize();
        quitButton.setBounds(World.SCENE_WIDTH - size.width - BUTTON_X_PADDING,
                                BUTTON_Y_PADDING,
                                size.width,
                                size.height);
        this.add(quitButton);

        quitButton.addActionListener(e -> {
            funGame.dispose();
            new FunGame();
        });
    }
}
