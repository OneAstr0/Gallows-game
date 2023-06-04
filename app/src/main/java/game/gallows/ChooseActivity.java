package game.gallows;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ChooseActivity extends AppCompatActivity {
    ImageButton btnBack, btnAnimals, btnFlora, btnCountry, btnFood;
    MediaPlayer sheet1;
    String[] animalsArray, floraArray, countryArray, foodArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        btnBack = findViewById(R.id.btnBackChoose);
        sheet1 = MediaPlayer.create(this, R.raw.sheet1);
        btnAnimals = findViewById(R.id.btnAnimals);
        btnFlora = findViewById(R.id.btnFlora);
        btnCountry = findViewById(R.id.btnCountry);
        btnFood = findViewById(R.id.btnFood);


        Resources res = getResources();
        animalsArray = res.getStringArray(R.array.animals25);
        floraArray = res.getStringArray(R.array.flora);
        countryArray = res.getStringArray(R.array.countryArray);
        foodArray = res.getStringArray(R.array.foodArray);

        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sheet1.isPlaying()) {
                        sheet1.pause();
                        sheet1.seekTo(0);
                    }
                    sheet1.start();

                    finish();
                }
            });
        }

        Intent intent = new Intent(ChooseActivity.this, GameActivity.class);


        if (btnAnimals != null) {
            btnAnimals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sheet1.isPlaying()) {
                        sheet1.pause();
                        sheet1.seekTo(0);
                    }
                    sheet1.start();

                    intent.putExtra("array", animalsArray);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (btnFlora != null) {
            btnFlora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sheet1.isPlaying()) {
                        sheet1.pause();
                        sheet1.seekTo(0);
                    }
                    sheet1.start();

                    intent.putExtra("array", floraArray);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (btnCountry != null) {
            btnCountry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sheet1.isPlaying()) {
                        sheet1.pause();
                        sheet1.seekTo(0);
                    }
                    sheet1.start();

                    intent.putExtra("array", countryArray);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (btnFood != null) {
            btnFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sheet1.isPlaying()) {
                        sheet1.pause();
                        sheet1.seekTo(0);
                    }
                    sheet1.start();

                    intent.putExtra("array", foodArray);
                    startActivity(intent);
                    finish();
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