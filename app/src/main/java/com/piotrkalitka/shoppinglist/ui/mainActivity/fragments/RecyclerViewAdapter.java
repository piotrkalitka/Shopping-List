package com.piotrkalitka.shoppinglist.ui.mainActivity.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.piotrkalitka.shoppinglist.db.ListModel;

import java.util.Collections;
import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private List<ListModel> lists;
    private ListViewHolder.OnRecyclerViewItemClicked onRecyclerViewItemClicked;

    RecyclerViewAdapter(List<ListModel> lists, ListViewHolder.OnRecyclerViewItemClicked onRecyclerViewItemClicked) {
        this.lists = lists;
        this.onRecyclerViewItemClicked = onRecyclerViewItemClicked;
        Collections.sort(lists);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(ListViewHolder.getView(parent), onRecyclerViewItemClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

}
