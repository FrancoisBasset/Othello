package com.groupe17.othello;

import android.content.Context;
import android.net.ConnectivityManager;
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

        lastGames_listView.setAdapter(null);

        if (IsOnline()) {
            new FirestoreService(this).getAllResults();
        } else {
            List<Result> results = new SqliteService(this).getAllResults();
            lastGames_listView.setAdapter(new ResultAdapter(this, results));
        }

    }

    public boolean IsOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}