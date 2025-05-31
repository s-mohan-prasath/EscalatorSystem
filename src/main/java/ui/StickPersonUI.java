package ui;

import constants.GlobalParams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StickPersonUI extends JPanel{
    private int X;  // X-position of the person
    private int Y;  // Y-position of the person

    private int FLOOR_NO;
    private final int START_X = 50;  // Starting X position
    private int START_Y;
    private final int WALK_SPEED = 2;    // Walking speed
    private Timer moveTimer;
    private boolean isWalking = false;   // Walking state
    private double stepAngle = 0;        // For walking animation
    private boolean movingRight = true;  // Direction of movement
    private double bounceOffset = 0;     // For bouncing while walking
    private boolean isExiting = false;
    private Runnable onExitComplete;

    private enum WalkMode {
        IDLE, NEAR_ELEVATOR, INSIDE_ELEVATOR, BACK_TO_START
    }
    private WalkMode currentMode = WalkMode.IDLE;
    private final int NEAR_ELEVATOR_X = GlobalParams.FLOOR_WIDTH-GlobalParams.ELEVATOR_WIDTH-70;  // adjust as per elevator position
    private final int INSIDE_ELEVATOR_X = NEAR_ELEVATOR_X+GlobalParams.ELEVATOR_WIDTH/2; // inside elevator X


    public StickPersonUI(int n) {
        this.X = START_X;
        this.FLOOR_NO = GlobalParams.TOTAL_FLOORS-n-1;
        setPreferredSize(new Dimension(GlobalParams.FLOOR_WIDTH, GlobalParams.FLOOR_HEIGHT));
        setOpaque(true);
        START_Y = getStartY(n);
        setVisible(true);

        // Timer for walking animation
        moveTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWalking) {
                    stepAngle += 0.2;
                    if (stepAngle >= 360) stepAngle = 0;
                    bounceOffset = Math.abs(Math.sin(stepAngle * 2)) * 2;

                    switch (currentMode) {
                        case NEAR_ELEVATOR:
                            if (X < NEAR_ELEVATOR_X) {
                                X += WALK_SPEED;
                            } else {
                                stopWalking();
                                currentMode = WalkMode.IDLE;
                                if (onExitComplete != null) onExitComplete.run();
                            }
                            break;

                        case INSIDE_ELEVATOR:
                            if (X < INSIDE_ELEVATOR_X) {
                                X += WALK_SPEED;
                            } else {
                                stopWalking();
                                currentMode = WalkMode.IDLE;
                                if (onExitComplete != null) onExitComplete.run();
                            }
                            break;

                        case BACK_TO_START:
                            if (X > START_X) {
                                X -= WALK_SPEED;
                            } else {
                                stopWalking();
                                currentMode = WalkMode.IDLE;
                                if (onExitComplete != null) onExitComplete.run();
                            }
                            break;

                        case IDLE:
                            // do nothing
                            break;
                    }

                    repaint();
                }
            }
        });

    }
    private int getStartY(int n){
        return (GlobalParams.TOTAL_FLOORS-n-1)*GlobalParams.FLOOR_HEIGHT-GlobalParams.PERSON_HEIGHT;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Draw the walking person
        g2d.setColor(Color.BLACK);
        drawWalkingPerson(g2d, X, getHeight() - GlobalParams.PERSON_HEIGHT / 2 - (int)bounceOffset);
        setVisible(true);
    }
    private void drawWalkingPerson(Graphics2D g, int x, int y) {
        // Calculate angles for smooth movement
        double armAngle = Math.sin(stepAngle) * 0.5;
        double legAngle = Math.sin(stepAngle) * 0.7;

        // Head with slight tilt
        double headTilt = Math.sin(stepAngle * 2) * 0.1;
        g.rotate(headTilt, x, y - 15);
        g.drawOval(x - 4, y - 19, 8, 8);
        g.rotate(-headTilt, x, y - 15);

        // Body with slight lean
        double bodyLean = Math.sin(stepAngle) * 0.1;
        g.rotate(bodyLean, x, y);
        g.drawLine(x, y - 11, x, y + 5);
        g.rotate(-bodyLean, x, y);

        // Arms with natural swing
        // Left arm
        g.rotate(armAngle, x, y - 5);
        g.drawLine(x, y - 5, x - 10, y - 2);
        g.rotate(-armAngle, x, y - 5);

        // Right arm (opposite phase)
        g.rotate(-armAngle, x, y - 5);
        g.drawLine(x, y - 5, x + 10, y - 2);
        g.rotate(armAngle, x, y - 5);

        // Legs with natural walking motion
        // Left leg
        g.rotate(legAngle, x, y + 5);
        g.drawLine(x, y + 5, x - 8, y + 20);
        g.rotate(-legAngle, x, y + 5);

        // Right leg (opposite phase)
        g.rotate(-legAngle, x, y + 5);
        g.drawLine(x, y + 5, x + 8, y + 20);
        g.rotate(legAngle, x, y + 5);
    }
    public void startWalking() {
        isWalking = true;
        currentMode = WalkMode.NEAR_ELEVATOR;
        moveTimer.start();
    }
    public void stopWalking() {
        isWalking = false;
        moveTimer.stop();
    }
    public void startExiting(Runnable onComplete) {
        isExiting = true;
        onExitComplete = onComplete;
        X = getWidth() - 50; // Start from right side
        movingRight = false;
        isWalking = false; // Reset walking state
        moveTimer.stop();
        revalidate();
        repaint();
        startWalking();
    }
    public void reset() {
        isExiting = false;
        onExitComplete = null;
        X = START_X;
        movingRight = true;
        isWalking = false;
        if (moveTimer.isRunning()) {
            moveTimer.stop();
        }
    }

    public void setWalkModeToIDLE(){
        currentMode = WalkMode.IDLE;
    }
    public void setWalkModeToNEARELEVATOR(){
        currentMode = WalkMode.NEAR_ELEVATOR;
    }
    public void setWalkModeToINSIDEELEVATOR(){
        currentMode = WalkMode.INSIDE_ELEVATOR;
    }
    public void setWalkModeToBACKTOSTART(){
        currentMode = WalkMode.BACK_TO_START;
    }
}
