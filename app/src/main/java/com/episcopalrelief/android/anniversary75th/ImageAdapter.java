package com.episcopalrelief.android.anniversary75th;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by Hunter on 8/29/14.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // references to our images
    private Integer[][] mThumbIds = {
            {R.drawable.full1a}, {R.drawable.full2a},
            {R.drawable.full3a}, {R.drawable.full4a},
            {R.drawable.full5a}, {R.drawable.full6a},
            {R.drawable.full7a, R.drawable.full7b, R.drawable.full7c, R.drawable.full7d, R.drawable.full7e}, {R.drawable.full8a},
            {R.drawable.full9a, R.drawable.full9b, R.drawable.full9d, R.drawable.full9e, R.drawable.full9f, R.drawable.full9g}, {R.drawable.full10a},
            {R.drawable.full11a}, {R.drawable.full12a},
            {R.drawable.full13a}, {R.drawable.full14a},
            {R.drawable.full15a}, {R.drawable.full16a},
            {R.drawable.full17a}, {R.drawable.full18a},
            {R.drawable.full19a}, {R.drawable.full20a},
            {R.drawable.full21a}, {R.drawable.full22a}
    };


    private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();


    public ImageAdapter(Context c) {
        mContext = c;
        for(int i = 0; i < mThumbIds.length; i++) {
            Integer resourceId = mThumbIds[i][0];

            bitmapArray.add(decodeSampledBitmapFromResource(mContext.getResources(), resourceId, 250, 250));
        }

        //special thumbnail
        bitmapArray.add(makeBitmapForSpecialThumb(mContext.getResources(), R.drawable.special_thumb, 250, 250));

    }

    public int getCount() {
        return mThumbIds.length+1;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

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

        Bitmap bitMap = BitmapFactory.decodeResource(res, resId);

        bitMap = ThumbnailUtils.extractThumbnail(bitMap, reqWidth, reqHeight);

        return bitMap;
    }

    public Bitmap makeBitmapForSpecialThumb(Resources res,
                                            int resId, int reqWidth, int reqHeight){
        Bitmap bitMap = BitmapFactory.decodeResource(res, resId);

        return bitMap;
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
            imageView.setPadding(4, 4, 4, 4);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);

        imageView.setImageBitmap(bitmapArray.get(position));
        //System.out.println(position);
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