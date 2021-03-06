package com.gymteam.tom.gymteam;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyTabsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public BrowseFragment tabBrowse;
    public MyInvitesFragment tabMyInvites;

    public MyTabsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                tabMyInvites = new MyInvitesFragment();
                return tabMyInvites;

            case 1:

                tabBrowse = new BrowseFragment();
                return tabBrowse;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}