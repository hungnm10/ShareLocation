
package com.huho.android.sharelocation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.main.events.EventFragment;
import com.huho.android.sharelocation.main.gmaps.MapsActivity;
import com.huho.android.sharelocation.main.tours.TourFragment;

/**
 * Main Activity to show both Bottom bar and Tab bar
 */

public class BottomMainActivity extends BaseActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @InjectView(R.id.container)
    protected ViewPager mViewPager;

    @InjectView(R.id.tabs)
    protected TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.cardview_light_background),
                getResources().getColor(R.color.color_primary_red));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new EventFragment();
            switch (position) {
                case 0:
                    return new EventFragment();
                case 1:
                    return new TourFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.event_tab);
                case 1:
                    return getString(R.string.tour_tab);
            }
            return null;
        }
    }
    @OnClick (R.id.btn_add_channel)
    public void addNewChannel(){
        Intent intent = new Intent(getApplicationContext(), CreateChannelActivity.class);
        startActivity(intent);
    }

}
