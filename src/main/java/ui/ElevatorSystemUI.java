package ui;

import constants.GlobalParams;

import javax.swing.*;
import java.awt.*;

import static constants.GlobalParams.*;

public class ElevatorSystemUI {
    private final JFrame frame;
    public FloorUI[] floors;
    private final ElevatorUI elevatorUI;
    private final BuildingUI buildingUI;
    private final ControlPanel controlPanelUI;
    public int[] floorStop;
    int currentFloor = 0;
    Timer elevatorTimer;
    boolean movingUp;

    public ElevatorSystemUI(){
        frame = new JFrame("Escalator System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GlobalParams.WINDOW_WIDTH, GlobalParams.WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());

        floors = new FloorUI[GlobalParams.TOTAL_FLOORS];

        elevatorUI = new ElevatorUI();
        buildingUI = new BuildingUI(floors);
        controlPanelUI = new ControlPanel(this);

        buildingUI.add(elevatorUI,JLayeredPane.PALETTE_LAYER);
        frame.add(this.buildingUI,BorderLayout.WEST);
        frame.add(controlPanelUI,BorderLayout.EAST);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);

        floorStop = new int[5];
        currentFloor = 0;
        startElevatorMovement();
    }
    private int getYForFloor(int floor) {
        return (TOTAL_FLOORS - floor) * FLOOR_HEIGHT; // floor 0 is bottom, 4 is top
    }
    private int getFloorFromY(int y) {
        return TOTAL_FLOORS - (y / FLOOR_HEIGHT);
    }
    // Call this once to start the timer loop
    private void startElevatorMovement() {
        elevatorTimer = new Timer(16, e -> {
            if (ELEVATOR_TARGET_FLOOR != -1) {
                int targetY = getYForFloor(ELEVATOR_TARGET_FLOOR) - ELEVATOR_HEIGHT;
                int currentY = elevatorUI.getY();

                int dy = targetY - currentY;

                if (Math.abs(dy) <= 2) {
                    // Close enough to snap to position
                    elevatorUI.setLocation(elevatorUI.getX(), targetY);

                    // Reached the target floor
                    currentFloor = ELEVATOR_TARGET_FLOOR;
                    floorStop[ELEVATOR_TARGET_FLOOR]--; // Serve the request
                    System.out.println("Reached floor: " + currentFloor);
                    ELEVATOR_TARGET_FLOOR = -1;
                    IS_ELEVATOR_MOVING = false;
                } else {
                    // Move smoothly towards target
                    int step = (dy > 0) ? 2 : -2;
                    elevatorUI.setLocation(elevatorUI.getX(), currentY + step);
                }

                // Optionally update the current floor dynamically (if useful for UI):
                currentFloor = getFloorFromY(elevatorUI.getY());
            }
        });

        elevatorTimer.start();

        // Continuously check for new floor requests
        continuouslyCheckPeople();
    }
    private void continuouslyCheckPeople() {
        new Thread(() -> {
            while (true) {
                if (!IS_ELEVATOR_MOVING) {
                    for (int i = 0; i < floorStop.length; i++) {
                        if (floorStop[i] > 0 && i != currentFloor) {
                            ELEVATOR_TARGET_FLOOR = i;
                            IS_ELEVATOR_MOVING = true;
                            break;
                        }
                    }
                }

                try {
                    Thread.sleep(100); // Check every 100ms
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

}
