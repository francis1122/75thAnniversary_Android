package com.episcopalrelief.android.anniversary75th;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DetailsActivity extends FragmentActivity {

//    ImageView imageView;

    ViewPager mPager;
    ScreenSlidePagerAdapter mAdapter;
    MediaPlayer mPlayer;
    ScreenSlidePageFragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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

        mPlayer = new MediaPlayer();
        String fileName = "android.resource://" + getPackageName() + "/" + R.raw.audio1;
        try {
            mPlayer.setDataSource(this, Uri.parse(fileName));
            //mPlayer.setDisplay(_holder); //_holder is SurfaceHolder of SurfaceView
            mPlayer.prepare();
            mPlayer.start();
        }catch (Exception e) {
            Toast.makeText(DetailsActivity.this, "ERROR: audio player not working.", Toast.LENGTH_LONG).show();
        }

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public static class ScreenSlidePagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 21;
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
            if(currentFragment != null) {
                currentFragment.handler.removeCallbacks(currentFragment.runnable);
            }
            currentFragment = (ScreenSlidePageFragment) mAdapter.instantiateItem(mPager, position);

            currentFragment.handler.postDelayed(currentFragment.runnable, currentFragment.interval);
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
