package ui;

import constants.GlobalParams;

import javax.swing.*;
import java.awt.*;

public class StickPersonUI extends JPanel{
    private int X;  // X-position of the person
    private int Y;  // Y-position of the person

    private int FLOOR_NO;
    private final int START_X = 50;  // Starting X position
    private int START_Y;  // Adjusted Y position (moved up since no ground line)
    private final int WALK_SPEED = 2;    // Walking speed
    private Timer moveTimer;
    private boolean isWalking = false;   // Walking state
    private double stepAngle = 0;        // For walking animation
    private boolean movingRight = true;  // Direction of movement
    private double bounceOffset = 0;     // For bouncing while walking
    private boolean isExiting = false;
    private Runnable onExitComplete;

    public StickPersonUI(int n) {
        this.FLOOR_NO = GlobalParams.TOTAL_FLOORS-n-1;
        setPreferredSize(new Dimension(GlobalParams.FLOOR_WIDTH, GlobalParams.PERSON_HEIGHT));
        START_Y = getStartX(n);
    }
    private int getStartX(int n){
        return n*GlobalParams.TOTAL_FLOORS-1;
    }

}
