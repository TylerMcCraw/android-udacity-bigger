package com.udacity.gradle.jokeprovider;

public class JokeProvider {

    // Static list of jokes as strings
    // Using a static array list here instead of an enum,
    // because using enums is expensive.
    private static final String[] jokes = new String[] {
            "There are 10 kinds of people in the world... Those who understand binary, and those who don't.",
            "Did you hear about the mathematician who's afraid of negative numbers? He'll stop at nothing to avoid them.",
            "Helvetica and Times New Roman walk into a bar. \"Get out of here!\" shouts the bartender. \"We don't serve your type.\"",
            "News has just come in that The Mars Rover has discovered a member of the feline species while exploring. Unfortunately, Curiosity killed the cat."
    };
    private static JokeProvider instance;

    public static synchronized JokeProvider getInstance() {
        if (instance == null) {
            instance = new JokeProvider();
        }
        return instance;
    }

    public int getNumberOfJokes() {
        return jokes.length;
    }

    public String getJoke(int joke) {
        int position = joke % jokes.length;
        if (position >= 0 && position < jokes.length) {
            return jokes[position];
        }
        System.out.println("JokeProvider.getJoke(): Could not find a suitable joke.");
        return "";
    }
}

