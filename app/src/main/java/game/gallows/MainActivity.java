package game.gallows;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
    ImageButton btnGame, btnRules, btnSettings;
    MediaPlayer sheet1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGame = findViewById(R.id.btnGame);
        btnRules = findViewById(R.id.btnRules);
        btnSettings = findViewById(R.id.btnSettings);

        sheet1 = MediaPlayer.create(this, R.raw.sheet1);

        if (btnGame != null) {
            btnGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sheet1.isPlaying()) {
                        sheet1.pause();
                        sheet1.seekTo(0);
                    }
                    sheet1.start();

                    Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
                    startActivity(intent);

                }
            });
        }

        if (btnRules != null) {
            btnRules.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sheet1.isPlaying()) {
                        sheet1.pause();
                        sheet1.seekTo(0);
                    }
                    sheet1.start();

                    Intent intent = new Intent(MainActivity.this, RulesActivity.class);
                    startActivity(intent);

                }
            });
        }

        if (btnSettings != null) {
            btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sheet1.isPlaying()) {
                        sheet1.pause();
                        sheet1.seekTo(0);
                    }
                    sheet1.start();

                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sheet1 != null) {
            sheet1.release();
            sheet1 = null;
        }
    }
}