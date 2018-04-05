package com.piotrkalitka.shoppinglist.ui.mainActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.piotrkalitka.shoppinglist.R;
import com.piotrkalitka.shoppinglist.ui.mainActivity.fragments.RecyclerViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViewPager();
        initTabs();
    }

    private void initViewPager() {
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainPagerAdapter adapter = ((MainPagerAdapter) viewPager.getAdapter());
                assert adapter != null;
                adapter.refreshLists();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTabs() {
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setText(R.string.currentListsLabel);
        tabs.getTabAt(1).setText(R.string.archivedListsLabel);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void refreshLists() {
        MainPagerAdapter adapter = (MainPagerAdapter) viewPager.getAdapter();
        if (adapter != null) {
            ((RecyclerViewFragment) adapter.getItem(0)).updateRecyclerView();
            ((RecyclerViewFragment) adapter.getItem(1)).updateRecyclerView();
        }
    }

}