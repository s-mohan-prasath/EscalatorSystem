package ui;

import constants.GlobalParams;

import javax.swing.*;
import java.awt.*;


public class BuildingUI extends JLayeredPane {
    FloorUI[] floors;
    public BuildingUI(FloorUI[] floors) {
        this.floors = floors;
        setOpaque(true);
        setPreferredSize(new Dimension(GlobalParams.FLOOR_WIDTH,GlobalParams.TOTAL_FLOORS*GlobalParams.FLOOR_HEIGHT));
        setSize(GlobalParams.ELEVATOR_WIDTH, GlobalParams.ELEVATOR_HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for (int i = 0; i < GlobalParams.TOTAL_FLOORS; i++) {
            floors[i] = new FloorUI(i);
//            floors[i].add(new StickPersonUI(i),BorderLayout.CENTER);
            add(floors[i],JLayeredPane.DEFAULT_LAYER);
        }
    }
}
