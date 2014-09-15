package com.episcopalrelief.android.anniversary75th;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Debug;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by Hunter on 8/29/14.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

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


    private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();


    public ImageAdapter(Context c) {
        mContext = c;
        Resources r = Resources.getSystem();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, r.getDisplayMetrics());
        for(int i = 0; i < mThumbIds.length; i++) {
            Integer resourceId = mThumbIds[i][0];


            bitmapArray.add(decodeSampledBitmapFromResource(mContext.getResources(), resourceId, px, px));
        }

        //special thumbnail
        bitmapArray.add(makeBitmapForSpecialThumb(mContext.getResources(), R.drawable.special_thumb, 360, 216));

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
            //imageView = new ImageView(mContext);


            //Calculation of ImageView Size - density independent.
            //maybe you should do this calculation not exactly in this method but put is somewhere else.
            Resources r = Resources.getSystem();
            int size[] = getScreenSize();
            float width = size[0];
            //
            //float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int)((width/4.3)), r.getDisplayMetrics());

            //Log.v("a", "test " + px);
            //Log.v("b", "width " + width);
            imageView = new ImageView(mContext);
            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams()
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams., LinearLayout.LayoutParams.FILL_PARENT);

            imageView.setAdjustViewBounds(true);

            imageView.setLayoutParams(new GridView.LayoutParams((int)((width/4.2)), (int)((width/4.2))));


            //parent.setLayoutParams();

            //imageView.setLayoutParams(new GridView.LayoutParams((int)px, (int)px));



            if(23 == position){
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }else {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
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
        }
        return new int[]{500, 500};
    }
}