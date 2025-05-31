package ui;

import constants.GlobalParams;

import javax.swing.*;
import java.awt.*;

import static constants.GlobalParams.*;

public class ElevatorUI extends JPanel {
    private final int X = FLOOR_WIDTH -ELEVATOR_WIDTH-5,Y=TOTAL_FLOORS*FLOOR_HEIGHT-ELEVATOR_HEIGHT-1;
    public ElevatorUI() {
        setOpaque(true);
        setBackground(new Color(120, 120, 255));
        setSize(ELEVATOR_WIDTH, ELEVATOR_HEIGHT);
        setBounds(X,Y, ELEVATOR_WIDTH, ELEVATOR_HEIGHT);

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Draw elevator shadow
        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);
        // Draw elevator body with gradient
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(120, 120, 255),
                0, getHeight(), new Color(80, 80, 200)
        );
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 15, 15);
        // Draw elevator door (animated)
        int doorWidth = (getWidth() - 14) / 2;
        int doorHeight = getHeight() - 14;
        int openOffset = (int)(doorWidth * GlobalParams.ELEVATOR_DOOR_SPEED);
        // Left door
        g2d.setColor(new Color(200, 200, 255));
        g2d.fillRoundRect(5 - openOffset, 5, doorWidth, doorHeight, 10, 10);
        // Right door
        g2d.fillRoundRect(5 + doorWidth + openOffset, 5, doorWidth, doorHeight, 10, 10);
        // Draw door outlines
        g2d.setColor(new Color(180, 180, 255));
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawRoundRect(5 - openOffset, 5, doorWidth, doorHeight, 10, 10);
        g2d.drawRoundRect(5 + doorWidth + openOffset, 5, doorWidth, doorHeight, 10, 10);
        // Draw floor indicator (top = 4, bottom = 0)
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        int yPos = getY();
        int floorNum = GlobalParams.TOTAL_FLOORS - 1 - (yPos / GlobalParams.FLOOR_HEIGHT);
        if (floorNum < 0) floorNum = 0;
        if (floorNum > GlobalParams.TOTAL_FLOORS - 1) floorNum = GlobalParams.TOTAL_FLOORS - 1;
        String floorText = String.valueOf(floorNum);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(floorText)) / 2;
        int textY = (getHeight() + fm.getAscent()) / 2;
        g2d.drawString(floorText, textX, textY);
    }
}
