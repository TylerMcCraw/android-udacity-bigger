package com.udacity.gradle.displayjoke;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokeDisplayFragment extends Fragment {

    private static final String BUNDLE_JOKE = "com.udacity.gradle.displayjoke.JokeDisplayFragment.JOKE";

    private String mJoke;

    public JokeDisplayFragment() {
        // Required empty public constructor
    }

    public static JokeDisplayFragment newInstance(String joke) {
        JokeDisplayFragment fragment = new JokeDisplayFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_JOKE, joke);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mJoke = getArguments().getString(BUNDLE_JOKE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_joke_display, container, false);
        ((TextView) rootView.findViewById(R.id.joke_text))
                .setText(mJoke != null ? mJoke : getString(R.string.empty_joke));
        return rootView;
    }
}
