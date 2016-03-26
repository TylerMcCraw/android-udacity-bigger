package com.udacity.gradle.builditbigger;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.udacity.gradle.displayjoke.JokeDisplayActivity;

public class MainActivity extends AppCompatActivity {

    private float defaultActionBarElevation = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO: there should be a better lifecycle event or method for setting back visibility
        toggleViewsVisibility(true);
    }

    private void toggleViewsVisibility(boolean backToInitial) {
        if (getSupportActionBar() != null) {
            if (backToInitial) {
                getSupportActionBar().setElevation(defaultActionBarElevation);
                getSupportActionBar().setTitle(getString(R.string.app_name));
            } else {
                defaultActionBarElevation = getSupportActionBar().getElevation();
                getSupportActionBar().setElevation(0);
                getSupportActionBar().setTitle("");
            }
        }

        View tellJokeButton = findViewById(R.id.tell_joke_button);
        if (tellJokeButton != null) {
            tellJokeButton.setVisibility(backToInitial ? View.VISIBLE : View.INVISIBLE);
        }
        View instructions = findViewById(R.id.instructions_text_view);
        if (instructions != null) {
            instructions.setVisibility(backToInitial ? View.VISIBLE : View.INVISIBLE);
        }

        View progressBar = findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility((backToInitial ? View.GONE : View.VISIBLE));
        }
        View rippleView = findViewById(R.id.splash_joke_view);
        if (rippleView != null) {
            rippleView.setVisibility(backToInitial ? View.INVISIBLE : View.VISIBLE);
        }
    }

    public void tellJoke(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final View fragmentContainer = findViewById(android.R.id.content);
            final View rippleView = findViewById(R.id.splash_joke_view);
            if (fragmentContainer != null
                    && rippleView != null) {
                int cx = fragmentContainer.getWidth() / 2;
                int cy = fragmentContainer.getHeight() / 2;
                int finalRadius = Math.max(fragmentContainer.getWidth(), fragmentContainer.getHeight());
                Animator anim = ViewAnimationUtils.createCircularReveal(rippleView, cx, cy, 0, finalRadius);
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
                // make the view invisible when the animation is done
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        toggleViewsVisibility(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        retrieveJokeAndDisplay();
                    }
                });
                anim.start();
            }
        } else {
            retrieveJokeAndDisplay();
        }
    }

    private void retrieveJokeAndDisplay() {
//        // This will display a joke using a local library
//        Random randomNum = new Random();
//        int randomJoke = randomNum.nextInt(JokeProvider.getInstance().getNumberOfJokes());
//        JokeDisplayActivity.openActivity(this, JokeProvider.getInstance().getJoke(randomJoke));

        // This will display a joke using GCE
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(MainActivity.this){
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                JokeDisplayActivity.openActivity(MainActivity.this, result);
                overridePendingTransition(0, 0);
            }
        };
        endpointsAsyncTask.execute(true);
    }
}
