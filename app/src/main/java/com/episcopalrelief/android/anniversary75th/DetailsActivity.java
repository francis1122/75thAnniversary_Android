package com.episcopalrelief.android.anniversary75th;

import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.List;

import static android.view.GestureDetector.SimpleOnGestureListener;

public class DetailsActivity extends FragmentActivity
{
    ImageView mPlayImage;
    ViewPager mPager;
    ScreenSlidePagerAdapter mAdapter;
    MediaPlayer mPlayer;
    ScreenSlidePageFragment currentFragment = null;
    DetailsActivity currentActivity = this;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mPlayImage = (ImageView) findViewById(R.id.playImage);
        mPlayImage.setBackgroundColor(Color.rgb(100, 100, 100));
        //mPlayImage.setImageResource(R.drawable.ic_action_pause);
        ImageViewAnimatedChange(this.getBaseContext(), mPlayImage, R.drawable.ic_action_pause);
        Intent intent = getIntent();
        int value = intent.getIntExtra("index", -1);



        /*Intent intent = getIntent();
        int value = intent.getIntExtra("index", -1)+1;
        Toast.makeText(DetailsActivity.this, "" + value , Toast.LENGTH_SHORT).show();

        imageView = (ImageView) findViewById(R.id.detailsImageButton);
        int resId = getResources().getIdentifier("full"+value+"a", "drawable", getPackageName());
        imageView.setImageResource(resId);
        */

        mAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);

        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(new DetailsOnPageChangeListener());
        mPager.setCurrentItem(value);
        mPlayer = new MediaPlayer();


        int resId = getResources().getIdentifier("audio"+(value+1), "raw", getPackageName());
        String fileName = "android.resource://" + getPackageName() + "/" + resId;
        try{
          mPlayer.setDataSource(DetailsActivity.this, Uri.parse(fileName));
        }catch (Exception e) {
            //Toast.makeText(DetailsActivity.this, "ERROR: audio player not working.", Toast.LENGTH_LONG).show();
            System.out.println("ERROR: audio player not working.");
        }
        mPlayer.prepareAsync();
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        mPlayer.start();


        mPager.setOnTouchListener(new ViewPager.OnTouchListener() {
            private float pointX;
            private float pointY;
            private int tolerance = 50;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        return false; //This is important, if you return TRUE the action of swipe will not take place.
                    case MotionEvent.ACTION_DOWN:
                        pointX = event.getX();
                        pointY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        boolean sameX = pointX + tolerance > event.getX() && pointX - tolerance < event.getX();
                        boolean sameY = pointY + tolerance > event.getY() && pointY - tolerance < event.getY();
                        if(sameX && sameY){
                            if(mPlayer.isPlaying()){
                                mPlayer.pause();
                                ImageViewAnimatedChange(currentActivity.getBaseContext(), mPlayImage, R.drawable.ic_action_pause);
                            }else{
                                mPlayer.start();
                                ImageViewAnimatedChange(currentActivity.getBaseContext(), mPlayImage, R.drawable.ic_action_play);
                            }

                            //The user "clicked" certain point in the screen or just returned to the same position an raised the finger
                        }
                }
                return false;
            }
        });

        /*
        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.goto_first);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        button = (Button)findViewById(R.id.goto_last);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(20);
            }
        });
        */
        //imageView.setImageResource(R.drawable.sample_4);j
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.details, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();


        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Enjoying the 75th Anniversary Celebration Photo Exhibition. #allhands75 https://www.facebook.com/EpiscopalRelief");
        sendIntent.setType("image/*");
        //File f = new File("android.resource://com.episcopalrelief.android.anniversary75th/" + R.drawable.full10a);
        Uri u = Uri.parse("android.resource://com.episcopalrelief.android.anniversary75th/" + R.drawable.full20a);
        //sendIntent.setData(u);
        sendIntent.putExtra(Intent.EXTRA_STREAM, u);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        this.setShareIntent(sendIntent);

        // Return true to display menu
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null){
            mPlayer.stop();
            if (isFinishing()){
                mPlayer.stop();
            }
        }
    }


    public static void ImageViewAnimatedChange(Context c, final ImageView v, final int imageID) {
        v.setAlpha(1.0f);
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        v.setImageResource(imageID);

        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setAlpha(0.0f);
            }
        });

        v.startAnimation(anim_out);
    }

    public static class ScreenSlidePagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 22;
        }

        @Override
        public Fragment getItem(int position) {

            return ScreenSlidePageFragment.create(position);
        }
    }

    public class DetailsOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position)
        {
            System.out.println("onPageSelected" + position);

            // mPager
            if (currentFragment != null) {
                currentFragment.handler.removeCallbacks(currentFragment.runnable);
            }
            currentFragment = (ScreenSlidePageFragment) mAdapter.instantiateItem(mPager, position);
            currentFragment.fragmentIndex = position;
            currentFragment.handler.postDelayed(currentFragment.runnable, currentFragment.interval);
            if (mPlayer != null) {

                mPlayer.stop();
                mPlayer.reset();
            }

            int resId = getResources().getIdentifier("audio" + (position + 1), "raw", getPackageName());

            String fileName = "android.resource://" + getPackageName() + "/" + resId;

                if (mPlayer != null) {
                    try {
                        mPlayer.setDataSource(DetailsActivity.this, Uri.parse(fileName));
                    } catch (Exception e) {
                        System.out.println("setDataSource for Audio Player ERROR");
                    }
                    mPlayer.prepareAsync();
                    mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });

                }
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
            //System.out.println("onPageScrollStateChanged" + state);

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            //System.out.println("onPageScrolled" + position);
        }

    }
}




