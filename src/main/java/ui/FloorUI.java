package ui;

import javax.swing.*;
import java.awt.*;

import static constants.GlobalParams.FLOOR_HEIGHT;
import static constants.GlobalParams.TOTAL_FLOORS;

public class FloorUI extends JPanel {
    private static int NUMBER;
    public FloorUI(int n) {
        this.NUMBER = TOTAL_FLOORS-n-1;
        setLayout(new BorderLayout());
        setBounds(0, NUMBER * FLOOR_HEIGHT, 600, FLOOR_HEIGHT);
        setBackground(new Color(240, 240, 240));
        // Label: Top panel is Floor 4, bottom is Floor 0
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createTitledBorder("Floor " + n)
        ));
    }
}
