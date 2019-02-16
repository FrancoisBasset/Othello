package com.groupe17.othello;

public class Result {
    private DiskColor diskColor;
    private double percent;

    public Result(DiskColor diskColor, double percent) {
        this.diskColor = diskColor;
        this.percent = percent;
    }

    public DiskColor getDiskColor() {
        return diskColor;
    }

    public double getPercent() {
        return percent;
    }
}
