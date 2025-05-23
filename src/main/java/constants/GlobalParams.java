package constants;

import java.awt.*;

public class GlobalParams {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    public static final int ELEVATOR_WIDTH = 80;
    public static final int ELEVATOR_HEIGHT = 80;
    public static final int ELEVATOR_SPEED = 80;
    public static final float ELEVATOR_DOOR_SPEED = 0.0f;

    public static final int TOTAL_FLOORS = 5;

    public static final int FLOOR_WIDTH = 600;
    public static final int FLOOR_HEIGHT = 150;

    public static final int PERSON_HEIGHT = 60;
    public GlobalParams(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        WINDOW_WIDTH=dimension.width;
        WINDOW_HEIGHT=dimension.height-100;
    }
}
