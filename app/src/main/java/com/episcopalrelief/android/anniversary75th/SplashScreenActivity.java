package com.episcopalrelief.android.anniversary75th;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

import javax.xml.transform.Result;

public class SplashScreenActivity extends Activity {

    private static final int SPLASH_SHOW_TIME = 2000;


    /**
     * Async Task: can be used to load DB, images during which the splash screen
     * is shown to user
     */

    private class BackgroundSplashTask<Object, Activity, Result>  extends AsyncTask<Object, Activity, Result>{


        @Override
        protected Result doInBackground(Object... paramses){

            // I have just given a sleep for this thread
            // if you want to load database, make
            // network calls, load images
            // you can do them here and remove the following
            // sleep

            // do not worry about this Thread.sleep
            // this is an async task, it will not disrupt the UI
            try {
                Thread.sleep(SPLASH_SHOW_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Result result){
            //  showDialog("Downloaded " + result + " bytes");
            super.onPostExecute(result);
            Intent i = new Intent(SplashScreenActivity.this,
                    GridActivity.class);
            // any info loaded can during splash_show
            // can be passed to main activity using
            // below
            i.putExtra("loaded_info", " ");
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //if()
        android.app.ActionBar bar = getActionBar();
        if(bar != null){
            bar.hide();
        }
        setContentView(R.layout.activity_splash_screen);
        BackgroundSplashTask<Object, Activity, Result> task = new BackgroundSplashTask<Object, Activity, Result>();

        task.execute();

    }


}