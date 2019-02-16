package com.groupe17.othello;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.WINDOW_SERVICE;

public class Board extends TableLayout {
    private int side;
    private DiskColor nextColor;
    private int lastBlank;

    private double whitePossession;
    private double blackPossession;

    public Board(Context context) {
        super(context);
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSide() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        side = dm.widthPixels;

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(side, side);
        layoutParams.bottomToBottom = 0;
        setLayoutParams(layoutParams);
    }

    public void initDisks() {
        side = Integer.valueOf(side / 8);
        lastBlank = 64;

        for (int line = 0; line < 8; line++) {
            TableRow row = new TableRow(getContext());

            for (int column = 0; column < 8; column++) {
                Disk disk = new Disk(getContext(), side, line, column, DiskColor.Blank);
                disk.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Disk disk = (Disk) v;
                        if (placeDisk(disk)) {
                            attackAllDirections(disk);
                        } else {
                            return;
                        }
                        if (lastBlank == 0) {
                            if (whitePossession > blackPossession) {
                                new SqliteService(getContext()).addOneGame("white", whitePossession);
                            } else {
                                new SqliteService(getContext()).addOneGame("black", blackPossession);
                            }

                            ((GameBoardActivity) getContext()).finish();
                        }

                        updatePossessions();
                    }
                });

                row.addView(disk);
            }

            addView(row);
        }

        draw4Disks();
        updatePossessions();
    }

    private Disk getDiskAt(int rowIndex, int columnIndex) {
        TableRow row = (TableRow) this.getChildAt(rowIndex);
        return (Disk) row.getChildAt(columnIndex);
    }

    private void draw4Disks() {
        simplePlaceDisk(getDiskAt(3, 3));
        simplePlaceDisk(getDiskAt(3, 4));
        simplePlaceDisk(getDiskAt(4, 4));
        simplePlaceDisk(getDiskAt(4, 3));
    }

    private void attackAllDirections(Disk disk) {
        for (AttackDirection direction : AttackDirection.values()) {
            attack(disk, direction);
        }
    }

    private void attack(Disk disk, AttackDirection attackDirection) {
        int x = disk.getLine() + attackDirection.getX();
        int y = disk.getColumn() + attackDirection.getY();

        if (x < 0 || y < 0 || x >= 8 || y >= 8) {
            return;
        }

        boolean canAttack = false;
        int numberOfVictims = 0;

        while (getDiskAt(x, y) != null) {
            if (getDiskAt(x, y).getColor() == disk.getColor()) {
                if (numberOfVictims >= 1) {
                    canAttack = true;
                    break;
                }
            } else if (getDiskAt(x, y).getColor() == DiskColor.Blank){
                return;
            } else {
                numberOfVictims++;
            }

            x += attackDirection.getX();
            y += attackDirection.getY();

            if (x < 0 || y < 0 || x >= 8 || y >= 8) {
                return;
            }
        }

        x = disk.getLine() + attackDirection.getX();
        y = disk.getColumn() + attackDirection.getY();

        if (canAttack) {
            while (getDiskAt(x, y).getColor() != disk.getColor()) {
                getDiskAt(x, y).setColor(disk.getColor());

                x += attackDirection.getX();
                y += attackDirection.getY();
            }
        }
    }

    private boolean placeDisk(Disk disk) {
        if  (!canMove(disk)) {
            return false;
        }

        simplePlaceDisk(disk);

        return true;
    }

    private void simplePlaceDisk(Disk disk) {
        if (nextColor == null) {
            nextColor = DiskColor.Black;
        }

        if (disk.getColor() == DiskColor.Blank) {
            disk.setColor(nextColor);
            switchNextColor();
            lastBlank--;
        }
    }

    private void switchNextColor() {
        if (nextColor == DiskColor.Black) {
            nextColor = DiskColor.White;
        } else {
            nextColor = DiskColor.Black;
        }
    }

    private boolean canMove(Disk disk) {
        int line = disk.getLine();
        int column = disk.getColumn();

        for (int l = line - 1; l <= line + 1; l++) {
            for (int c = column - 1; c <= column + 1; c++) {

                if (l < 0 || c < 0) {
                    continue;
                }
                if (l > 7 || c > 7) {
                    continue;
                }
                if (l == 0 && c == 0) {
                    continue;
                }

                Disk d = getDiskAt(l, c);

                if (d == null) {
                    continue;
                }

                if (d.getColor() != DiskColor.Blank) {
                    return true;
                }
            }
        }

        return false;
    }

    private void updatePossessions() {
        whitePossession = 0;
        blackPossession = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch(getDiskAt(i, j).getColor()) {
                    case White:
                        whitePossession++;
                        break;
                    case Black:
                        blackPossession++;
                        break;
                }
            }
        }

        double sum = whitePossession + blackPossession;

        whitePossession = Math.ceil(100 * (whitePossession / sum));
        blackPossession = 100 - whitePossession;

        TextView whitePossession_label = ((GameBoardActivity) getContext()).findViewById(R.id.whitePossession_label);
        TextView blackPossession_label = ((GameBoardActivity) getContext()).findViewById(R.id.blackPossession_label);

        whitePossession_label.setText(whitePossession + " %");
        blackPossession_label.setText(blackPossession + " %");
    }
}
