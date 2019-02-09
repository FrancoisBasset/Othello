package com.groupe17.othello;

import android.graphics.Color;

public enum DiskColor {
    White(Color.WHITE),
    Black(Color.BLACK),
    Blank(Color.TRANSPARENT);

    private int color;

    DiskColor(int color) {
        this.color = color;
    }
}
