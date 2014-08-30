package com.episcopalrelief.android.anniversary75th;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

/**
 * Created by Hunter on 8/29/14.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.full1a, R.drawable.full2a,
            R.drawable.full3a, R.drawable.full4a,
            R.drawable.full5a, R.drawable.full6a,
            R.drawable.full7a, R.drawable.full8a,
            R.drawable.full9a, R.drawable.full10a,
            R.drawable.full11a, R.drawable.full12a,
            R.drawable.full13a, R.drawable.full14a,
            R.drawable.full15a, R.drawable.full16a,
            R.drawable.full17a, R.drawable.full18a,
            R.drawable.full19a, R.drawable.full20a,
            R.drawable.full21a
    };


    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
           int size[] = getScreenSize();
            float width = size[0];
            imageView.setLayoutParams(new GridView.LayoutParams((int)((width/4.2)), (int)(width/4.2)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(10, 10, 10, 10);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        System.out.println(position);
        return imageView;
    }


    public int[] getScreenSize(){
        Point size = new Point();
        WindowManager w = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
            w.getDefaultDisplay().getSize(size);
            return new int[]{size.x, size.y};
        }else{
            Display d = w.getDefaultDisplay();
            //noinspection deprecation
            return new int[]{d.getWidth(), d.getHeight()};
        }
    }
}