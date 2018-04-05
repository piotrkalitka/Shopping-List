package com.piotrkalitka.shoppinglist.ui.mainActivity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.piotrkalitka.shoppinglist.Constants;
import com.piotrkalitka.shoppinglist.R;
import com.piotrkalitka.shoppinglist.db.DbHelper;
import com.piotrkalitka.shoppinglist.db.ListModel;
import com.piotrkalitka.shoppinglist.ui.listDetailsActivity.ListDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class RecyclerViewFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        if (getContext() != null) {
            DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
            recyclerView.addItemDecoration(itemDecoration);
        }

        updateRecyclerView();
    }

    public void updateRecyclerView() {
        List<ListModel> lists = DbHelper.getInstance(getContext()).getLists(showArchivedLists());
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(lists, this::onRecyclerViewItemClicked);
        recyclerView.setAdapter(adapter);
    }

    private void onRecyclerViewItemClicked(int position, int listId) {
        Intent intent = new Intent(getContext(), ListDetailsActivity.class);
        intent.putExtra(Constants.BUNDLE_LIST_ID, listId);
        startActivity(intent);
    }

    protected abstract boolean showArchivedLists();

}
