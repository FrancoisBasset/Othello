package com.groupe17.othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newGame_button = findViewById(R.id.newGame_button);
        newGame_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGameActivity();
            }
        });
    }

    public void startNewGameActivity() {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }
}
