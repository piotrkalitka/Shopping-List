package com.piotrkalitka.shoppinglist.ui.listDetailsActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.piotrkalitka.shoppinglist.db.DbHelper;

import java.util.ArrayList;
import java.util.List;

class ItemsListAdapter extends RecyclerView.Adapter {

    private Context context;
    private RecyclerView recyclerView;
    private List<String> items;
    private int listId;
    private boolean editMode;
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_FIELD = 1;

    ItemsListAdapter(Context context, int listId, List<String> items, boolean editMode) {
        if (items != null) {
            this.items = items;
        } else {
            this.items = new ArrayList<>();
        }
        this.context = context;
        this.listId = listId;
        this.editMode = editMode;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TEXT) {
            return new TextItemViewHolder(TextItemViewHolder.getView(parent), editMode, this::removeItem);
        } else {
            return new FieldItemViewHolder(FieldItemViewHolder.getView(parent), this::addItem);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_TEXT) {
            ((TextItemViewHolder) holder).bind(items.get(position));
        } else if (viewType == TYPE_FIELD) {
            ((FieldItemViewHolder) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        if (editMode) {
            return items.size() + 1;
        } else {
            return items.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (editMode) {
            if (position == items.size()) {
                return TYPE_FIELD;
            } else {
                return TYPE_TEXT;
            }
        } else return TYPE_TEXT;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    private void removeItem(String item) {
        DbHelper.getInstance(context).removeItem(listId, item);
        items.remove(item);
        notifyDataSetChanged();
    }

    private void addItem(String item) {
        DbHelper.getInstance(context).addItem(listId, item);
        items.add(item);
        notifyDataSetChanged();
        if (editMode) recyclerView.scrollToPosition(items.size());
    }

}
