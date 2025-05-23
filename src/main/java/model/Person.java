package model;

public class Person extends Thread{
    private final int startFloor;
    private final int endFloor;

    public Person(int startFloor, int endFloor) {
        this.startFloor = startFloor;
        this.endFloor = endFloor;
    }

    public int getStartFloor() {
        return startFloor;
    }

    public int getEndFloor() {
        return endFloor;
    }
}
