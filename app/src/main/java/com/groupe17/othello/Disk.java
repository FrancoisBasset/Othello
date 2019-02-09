package com.groupe17.othello;

import android.content.Context;
import android.view.View;
import android.widget.TableRow;

public class Disk extends android.support.v7.widget.AppCompatImageView {
    private int line;
    private int column;
    private DiskColor color;

    public Disk(Context context, int side, int line, int column, DiskColor color) {
        super(context);

        setColor(color);
        setLayoutParams(new TableRow.LayoutParams(side, side));

        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public DiskColor getColor() {
        return color;
    }

    public void setColor(DiskColor color) {
        this.color = color;

        switch (color) {
            case White:
                setImageResource(R.drawable.white);
                break;
            case Black:
                setImageResource(R.drawable.black);
                break;
        }
    }
}
