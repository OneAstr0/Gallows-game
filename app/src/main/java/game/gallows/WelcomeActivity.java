package game.gallows;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 3000; // Задержка в 3 секунды
    private static final long PROGRESS_BAR_ANIMATION_DURATION = 3000; // Продолжительность анимации полоски загрузки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView logoImage = findViewById(R.id.logoImageView);
        TextView s = findViewById(R.id.s);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoImage.startAnimation(fadeInAnimation);
        s.startAnimation(fadeInAnimation);


        ScaleAnimation progressBarAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f);
        progressBarAnimation.setDuration(PROGRESS_BAR_ANIMATION_DURATION);
        progressBar.setAnimation(progressBarAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}


