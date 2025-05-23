package ui;

import constants.GlobalParams;

import javax.swing.*;
import java.awt.*;

public class BuildingUI extends JLayeredPane {
    private FloorUI[] floors;
    public BuildingUI() {
        setOpaque(true);
        setPreferredSize(new Dimension(GlobalParams.FLOOR_WIDTH,GlobalParams.TOTAL_FLOORS*GlobalParams.FLOOR_HEIGHT));
        setSize(GlobalParams.ELEVATOR_WIDTH, GlobalParams.ELEVATOR_HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        floors = new FloorUI[GlobalParams.TOTAL_FLOORS];
        for (int i = 0; i < GlobalParams.TOTAL_FLOORS; i++) {
            floors[i] = new FloorUI(i);
            add(floors[i],JLayeredPane.DEFAULT_LAYER);
        }
    }
}
