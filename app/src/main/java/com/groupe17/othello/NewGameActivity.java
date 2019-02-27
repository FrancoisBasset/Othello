package com.groupe17.othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        Button localGame_button = findViewById(R.id.localGame_button);
        localGame_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameBoardActivity(v, "local");
            }
        });

    }

    public void startGameBoardActivity(View v, String mode) {
        Intent intent = new Intent(this, GameBoardActivity.class);
        intent.putExtra("mode", mode);

        startActivity(intent);


    }
}
