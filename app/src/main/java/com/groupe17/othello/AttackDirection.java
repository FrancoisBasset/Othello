package com.groupe17.othello;

public enum AttackDirection {
    North(-1, 0),
    South(1, 0),
    West(0, -1),
    East(0, 1),
    NorthWest(-1, -1),
    NorthEast(-1, 1),
    SouthWest(1, -1),
    SouthEast(1, 1);

    private int x;
    private int y;

    AttackDirection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
