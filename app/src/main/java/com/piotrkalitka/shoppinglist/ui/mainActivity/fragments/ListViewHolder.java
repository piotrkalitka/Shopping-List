package com.piotrkalitka.shoppinglist.ui.mainActivity.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piotrkalitka.shoppinglist.Constants;
import com.piotrkalitka.shoppinglist.R;
import com.piotrkalitka.shoppinglist.db.ListModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

class ListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtCreatedAt)
    TextView txtCreatedAt;

    private OnRecyclerViewItemClicked onRecyclerViewItemClicked;

    ListViewHolder(View itemView, OnRecyclerViewItemClicked onRecyclerViewItemClicked) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.onRecyclerViewItemClicked = onRecyclerViewItemClicked;
    }

    static View getView(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.list_view_holder, container, false);
    }

    void bind(ListModel model) {
        itemView.setOnClickListener(v -> onRecyclerViewItemClicked.onItemClicked(getAdapterPosition(), model.getId()));
        txtTitle.setText(itemView.getContext().getString(R.string.list_title, model.getTitle(), model.getItems().size()));

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.TIMESTAMP_TO_SHOW_SDF, Locale.getDefault());

        assert model.getDate() != null;
        txtCreatedAt.setText(sdf.format(model.getDate()));
    }


    public interface OnRecyclerViewItemClicked {
        void onItemClicked(int position, int listId);
    }

}
