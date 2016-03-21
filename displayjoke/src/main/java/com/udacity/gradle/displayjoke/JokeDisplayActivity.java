package com.udacity.gradle.displayjoke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class JokeDisplayActivity extends AppCompatActivity {

    private static final String BUNDLE_JOKE = "com.udacity.gradle.displayjoke.JokeDisplayActivity.JOKE";

    public static void openActivity(Activity callingActivity, String joke) {
        Intent jokeDisplayIntent = new Intent(callingActivity, JokeDisplayActivity.class);
        jokeDisplayIntent.putExtra(BUNDLE_JOKE, joke);
        callingActivity.startActivity(jokeDisplayIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        String joke = null;
        if (getIntent().getExtras() != null) {
            joke = getIntent().getStringExtra(BUNDLE_JOKE);
        }

        JokeDisplayFragment fragment = JokeDisplayFragment.newInstance(joke);
        getSupportFragmentManager().beginTransaction().add(
                android.R.id.content, fragment).commit();
    }
}
