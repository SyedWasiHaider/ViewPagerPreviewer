package com.wasihaider.viewpagerpreview;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.wasihaider.viewpagerpreviewer.ViewPagerPreviewer;


public class MainActivity extends ActionBarActivity implements Test.FragmentEventListener {

    ViewPagerPreviewer previewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(), 10));
        pager.setOffscreenPageLimit(8);
        previewer = (ViewPagerPreviewer)findViewById(R.id.previewer);
        previewer.setPager(pager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentAttached(int position) {
    }
}
