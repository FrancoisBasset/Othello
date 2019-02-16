package com.groupe17.othello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.List;

public class LastGamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_games);

        ListView lastGames_listView = findViewById(R.id.lastGames_listView);

        List<Result> results = new SqliteService(this).getAllResults();

        lastGames_listView.setAdapter(new ResultAdapter(this, results));
    }

}