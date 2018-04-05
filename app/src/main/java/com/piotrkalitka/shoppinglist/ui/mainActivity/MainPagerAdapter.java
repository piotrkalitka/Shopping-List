package com.piotrkalitka.shoppinglist.ui.mainActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.piotrkalitka.shoppinglist.ui.mainActivity.fragments.archivedListsFragment.ArchivedListsFragment;
import com.piotrkalitka.shoppinglist.ui.mainActivity.fragments.currentListsFragment.CurrentListsFragment;

class MainPagerAdapter extends FragmentStatePagerAdapter {

    MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private CurrentListsFragment currentListsFragment;
    private ArchivedListsFragment archivedListsFragment;

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (currentListsFragment == null) currentListsFragment = new CurrentListsFragment();
                return currentListsFragment;
            case 1:
                if (archivedListsFragment == null)
                    archivedListsFragment = new ArchivedListsFragment();
                return archivedListsFragment;
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void refreshLists() {
        currentListsFragment.updateRecyclerView();
        archivedListsFragment.updateRecyclerView();
    }
}
