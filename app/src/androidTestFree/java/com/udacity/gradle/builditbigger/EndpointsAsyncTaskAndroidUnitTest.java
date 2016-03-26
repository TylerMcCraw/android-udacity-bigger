package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import android.text.TextUtils;

/**
 * Created by Tyler McCraw on 3/25/16.
 */
public class EndpointsAsyncTaskAndroidUnitTest extends AndroidTestCase {

    Context context;

    public void setUp() throws Exception {
        super.setUp();

        context = new MockContext();
        setContext(context);
        assertNotNull(context);
    }

    public void testVerifyJokesLoadFromLocalGCE() {
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(getContext()) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                assertTrue(!TextUtils.isEmpty(result));
            }
        };
        endpointsAsyncTask.execute(false);
    }
}
