package com.groupe17.othello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SqliteService extends SQLiteOpenHelper {

    public SqliteService(Context context) {
        super(context, "othello", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table last_games(id INTEGER primary key AUTOINCREMENT, player text, percent double)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addOneGame(String player, double percent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("player", player);
        contentValues.put("percent", percent);

        this.getWritableDatabase().insert("last_games", null, contentValues);
    }

    public List<Result> getAllResults() {
        Cursor cursor = this.getReadableDatabase().query("last_games", null, null, null, null, null, "id desc");

        List<Result> results = new ArrayList<>();

        while (cursor.moveToNext()) {
            switch (cursor.getString(1)) {
                case "white":
                    results.add(new Result(DiskColor.White, cursor.getDouble(2)));
                    break;
                case "black":
                    results.add(new Result(DiskColor.Black, cursor.getDouble(2)));
                    break;
            }


        }

        return results;
    }
}
