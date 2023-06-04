package game.gallows;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnBack;
    ImageView gallows;
    LinearLayout layout;
    TextView[] slots;
    Button[] buttons;
    String randomWord;
    int fallsCount = 0;

    MediaPlayer sheet1, seehuman, win, lose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/halogen.ttf");
        gallows = findViewById(R.id.gallows);
        gallows.setImageResource(R.drawable.f0);

        // звуки
        sheet1 = MediaPlayer.create(this, R.raw.sheet1);
        seehuman = MediaPlayer.create(this, R.raw.see_human);
        win = MediaPlayer.create(this, R.raw.win);
        lose = MediaPlayer.create(this, R.raw.lose);

        Intent intent = getIntent();
        // Получаем массив из намерения
        String[] array = intent.getStringArrayExtra("array");

        // Беру случайное слово
        Random random = new Random();
        int randomIndex = random.nextInt(array.length);
        randomWord = array[randomIndex];
        randomWord = randomWord.toUpperCase();


        btnBack = findViewById(R.id.btnBackGame);

        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sheet1.isPlaying()) {
                        sheet1.pause();
                        sheet1.seekTo(0);
                    }
                    sheet1.start();

                    Intent intent = new Intent(GameActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }


        // работа с выводом
        layout = findViewById(R.id.layoutContainer);
        slots = new TextView[randomWord.length()];
        buttons = new Button[34];

        for (int i = 0; i < randomWord.length(); i++) {
            TextView textView = new TextView(this);
            textView.setText("_");
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
            textView.setTextColor(Color.BLACK);
            textView.setTypeface(typeface);
            textView.setPadding(12, 0, 12, 0);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            textView.setLayoutParams(layoutParams);

            layout.addView(textView);
            slots[i] = textView;
        }

        for (int i = 0; i < 34; i++) {
            int resID = getResources().getIdentifier("button_" + (i + 1), "id", getPackageName());
            Button button = findViewById(resID);
            button.setOnClickListener(this);
            buttons[i] = button;
        }

        openFirstAndLastLetter(); // Открываем первую и последнюю букву

        /*
        slots[0].setText(String.valueOf(randomWord.charAt(0))); // Открываем первую букву
        slots[randomWord.length() - 1].setText(String.valueOf(randomWord.charAt(randomWord.length() - 1))); // Открываем последнюю букву
        */

    }

    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        clickedButton.setEnabled(false); // Отключаем кнопку

        char clickedLetter = clickedButton.getText().charAt(0);

        boolean letterFound = false;
        for (int i = 0; i < randomWord.length(); i++) {
            if (randomWord.charAt(i) == clickedLetter) {
                letterFound = true;
                slots[i].setText(String.valueOf(clickedLetter)); // Открываем соответствующий слот буквы
            }
        }

        if (letterFound) {
            clickedButton.setTextColor(Color.GREEN); // Меняем цвет текста на зеленый
        } else {
            fallsCount++; // Увеличиваем счетчик осечек
            clickedButton.setTextColor(Color.RED); // Меняем цвет текста на красный


            // старый метод выхода при поражении без окна
//            if (fallsCount >= 9) {
//                Toast.makeText(this, "Проигрыш!", Toast.LENGTH_SHORT).show();
//                finish();
//                Intent intent = new Intent(GameActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
        }


        // обновление картинки
        switch (fallsCount) {
            case 1:
                gallows.setImageResource(R.drawable.f1);
                break;

            case 2:
                gallows.setImageResource(R.drawable.f2);
                break;

            case 3:
                gallows.setImageResource(R.drawable.f3);
                break;

            case 4:
                gallows.setImageResource(R.drawable.f4);
                break;

            case 5:
                gallows.setImageResource(R.drawable.f5);
                break;

            case 6:
                gallows.setImageResource(R.drawable.f6);
                break;

            case 7:
                gallows.setImageResource(R.drawable.f7);
                break;

            case 8:
                gallows.setImageResource(R.drawable.f8);

                if (seehuman.isPlaying()) {
                    seehuman.pause();
                    seehuman.seekTo(0);
                }
                seehuman.start();

                break;

            case 9:
                gallows.setImageResource(R.drawable.f9);

                if (lose.isPlaying()) {
                    lose.pause();
                    lose.seekTo(0);
                }
                lose.start();


                // Создание оверлея
                showEndGameDialog(false); // Вызов окна при победе


                break;

        }


        checkWinCondition();
    }

    private void checkWinCondition() {
        for (TextView slot : slots) {
            if (slot.getText().toString().equals("_")) {
                return;
            }
        }

        if (win.isPlaying()) {
            win.pause();
            win.seekTo(0);
        }
        win.start();

        showEndGameDialog(true); // Вызов окна при победе
    }


    private void openFirstAndLastLetter() {
        char firstLetter = randomWord.charAt(0);
        char lastLetter = randomWord.charAt(randomWord.length() - 1);
        Button firstButton = null;
        Button lastButton = null;

        for (Button button : buttons) {
            if (button != null) {
                char letter = button.getText().charAt(0);
                if (letter == firstLetter) {
                    firstButton = button;
                    //debug: Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                }
                if (letter == lastLetter) {
                    lastButton = button;
                    //debug: Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (firstButton != null) {
            firstButton.setEnabled(false);
            firstButton.setTextColor(Color.GRAY);
        }

        if (lastButton != null) {
            lastButton.setEnabled(false);
            lastButton.setTextColor(Color.GRAY);
        }

        for (int i = 0; i < randomWord.length(); i++) {
            char letter = randomWord.charAt(i);
            if (letter == firstLetter) {
                slots[i].setText(String.valueOf(letter));
            }
            if (letter == lastLetter) {
                slots[i].setText(String.valueOf(letter));
            }
        }
    }


    private void showEndGameDialog(boolean isWin) {
        // Создание диалогового окна


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.win_screen, null);
        builder.setView(dialogView);

        // Настройка фонового рисунка окна (можно настроить по своему усмотрению)
        AlertDialog endGameDialog = builder.create();
        endGameDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        endGameDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

        // Настройка кнопок и текстового поля
        Button btnRestart = dialogView.findViewById(R.id.btnRestart);
        Button btnMenu = dialogView.findViewById(R.id.btnMenu);
        TextView result = dialogView.findViewById(R.id.result);
        TextView word = dialogView.findViewById(R.id.word);
        TextView falls = dialogView.findViewById(R.id.falls);

        // Установка текста и обработчиков кликов кнопок
        if (isWin) {
            result.setText("Вы угадали слово!");
        } else {
            result.setText("Вы не угадали слово!");
        }
        word.setText("Загаданное слово: " + randomWord);
        falls.setText("Количество ошибок: " + String.valueOf(fallsCount) + " из 9");

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, ChooseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Отображение диалогового окна
        endGameDialog.show();

        endGameDialog.setCancelable(false);
        endGameDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sheet1 != null) {
            sheet1.release();
            sheet1 = null;
        }

        if (seehuman != null) {
            seehuman.release();
            seehuman = null;
        }

        if (win != null) {
            win.release();
            win = null;
        }

        if (lose != null) {
            lose.release();
            lose = null;
        }
    }

}