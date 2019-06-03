package com.akudama;

public class Ship {

    private int size;
    private Coordinates headPosition;
    private Direction direction;

    public Ship(int size, Coordinates headPosition, Direction direction) {
        this.size = size;
        this.headPosition = headPosition;
        this.direction = direction;
    }

    public int getSize() {
        return size;
    }

    public Coordinates getHeadPosition() {
        return headPosition;
    }

    public Direction getDirection() {
        return direction;
    }
}
