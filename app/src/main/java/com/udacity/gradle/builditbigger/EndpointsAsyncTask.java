package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.backend.jokeApi.JokeApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Boolean, Void, String> {

    private static final String TAG = "EndpointsAsyncTask";
    private static JokeApi mJokeService = null;
    private Context context;

    public EndpointsAsyncTask(Context context) {
        this.context = context;
    }

    /**
     * Execute an asynchronous background task to retrieve a joke from
     * either a GCE local development server or a deployed/live GCE server
     * @param params boolean true if to retrieve a joke from our deployed GCE server
     * @return String joke
     */
    @Override
    protected String doInBackground(Boolean... params) {
        if(mJokeService == null) {  // Only do this once
            JokeApi.Builder builder;

            boolean deployedServer = params[0];
            if (deployedServer) {
                // GCE DEPLOYMENT
                builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl(context.getString(R.string.api_endpoint));

            } else {
                // LOCAL DEPLOYMENT
                builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            }
            mJokeService = builder.build();
        }

        try {
            return mJokeService.getJoke().execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "onPostExecute: result= " + result);
    }
}