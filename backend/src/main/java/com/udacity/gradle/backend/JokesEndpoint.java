/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.udacity.gradle.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.gradle.jokeprovider.JokeProvider;

import java.util.Random;

/** An endpoint class we are exposing */
@Api(
  name = "jokeApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.gradle.udacity.com",
    ownerName = "backend.gradle.udacity.com",
    packagePath=""
  )
)
public class JokesEndpoint {

    /** A simple endpoint method that returns a random joke */
    @ApiMethod(name = "getJoke")
    public JokeBean getJoke() {
        JokeBean response = new JokeBean();
        Random randomNum = new Random();
        int randomJoke = randomNum.nextInt(JokeProvider.getInstance().getNumberOfJokes());
        String joke = JokeProvider.getInstance().getJoke(randomJoke);
        response.setJoke(joke != null ? joke : "");

        return response;
    }
}
