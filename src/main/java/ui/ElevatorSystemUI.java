package ui;

import constants.GlobalParams;

import javax.swing.*;
import java.awt.*;

public class ElevatorSystemUI {
    private final JFrame frame;
    private final ElevatorUI elevatorUI;
    private final BuildingUI buildingUI;

    public ElevatorSystemUI(){
        frame = new JFrame("Escalator System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GlobalParams.WINDOW_WIDTH, GlobalParams.WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());

        elevatorUI = new ElevatorUI();
        buildingUI = new BuildingUI();

        buildingUI.add(elevatorUI,JLayeredPane.PALETTE_LAYER);
        frame.add(this.buildingUI,BorderLayout.WEST);

        frame.setVisible(true);
    }
}
