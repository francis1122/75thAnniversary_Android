/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.episcopalrelief.android.anniversary75th;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *

 */
public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */

    public int fragmentIndex = -1;

    private int pictureIndex = 0;
    // references to our images
    private Integer[][] mThumbIds = {
            {R.drawable.full0a},
            {R.drawable.full1a}, {R.drawable.full2a},
            {R.drawable.full3a}, {R.drawable.full4a},
            {R.drawable.full5a}, {R.drawable.full6a},
            {R.drawable.full7a},
            {R.drawable.full8a, R.drawable.full8b, R.drawable.full8c, R.drawable.full8d, R.drawable.full8e},
            {R.drawable.full9a},
            {R.drawable.full10a, R.drawable.full10b, R.drawable.full10c, R.drawable.full10d, R.drawable.full10e, R.drawable.full10f, R.drawable.full10g},
            {R.drawable.full11a}, {R.drawable.full12a},
            {R.drawable.full13a}, {R.drawable.full14a},
            {R.drawable.full15a}, {R.drawable.full16a},
            {R.drawable.full17a}, {R.drawable.full18a},
            {R.drawable.full19a}, {R.drawable.full20a},
            {R.drawable.full21a}, {R.drawable.full22a}
    };

    public Integer mainImage;

    public static final String ARG_PAGE = "page";
    public ImageView imageView;
    public Activity parentActivity;
    public Handler handler = new Handler();
    public Runnable runnable = new Runnable(){
        public void run() {
            //Toast.makeText(MyActivity.this, "C'Mom no hands!", Toast.LENGTH_SHORT).show();
            System.out.println("this is a working timer");

            //mThumbIds
            int arrayLength = mThumbIds[fragmentIndex].length;
            if(arrayLength > 1){
                pictureIndex++;
                if(pictureIndex == arrayLength){
                    pictureIndex = 0;
                }
                Bitmap bitmapImage = decodeSampledBitmapFromResource(parentActivity.getApplicationContext().getResources(), mThumbIds[fragmentIndex][pictureIndex], 500, 500);
                ImageViewAnimatedChange(parentActivity.getApplicationContext(), imageView, bitmapImage);
//                imageView.setImageBitmap(bitmapImage);

                handler.postDelayed(runnable, interval);
            }
        }
    };

    long startTime = 0;

    public final int interval = 8000; // 8 Second


    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;



    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res,
                                                  int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;


        return BitmapFactory.decodeResource(res, resId, options);
    }



    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public static void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    public ScreenSlidePageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);


        // Set the title view to show the page number.
        parentActivity = this.getActivity();
        imageView = (ImageView) rootView.findViewById(R.id.detailsImage);
        int resId = getResources().getIdentifier("full"+(mPageNumber+1) +"a", "drawable", this.getActivity().getPackageName());
        mainImage = resId;

  //      Bitmap bitmapImage = decodeSampledBitmapFromResource(parentActivity.getApplicationContext().getResources(), mThumbIds[fragmentIndex][pictureIndex], 500, 500);
//        imageView.setImageBitmap(bitmapImage);
        imageView.setImageResource(resId);


        //DO IMAGE ROTATION IF required
        //handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        //handler.postDelayed(runnable, interval);


        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        //TODO: stop or handler, w/e
    }



}
