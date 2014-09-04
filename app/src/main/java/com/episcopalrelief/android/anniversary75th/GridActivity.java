package com.episcopalrelief.android.anniversary75th;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.io.File;


public class GridActivity extends Activity {

    private ShareActionProvider mShareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent myIntent = new Intent(GridActivity.this, DetailsActivity.class);
                myIntent.putExtra("index", position);
                GridActivity.this.startActivity(myIntent);


                //Toast.makeText(GridActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.grid, menu);

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
        if (id == R.id.menu_item_donate) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.episcopalrelief.org/what-you-can-do/donate-now/individual-donation/75th-fundraising-campaigns"));
            startActivity(browserIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
