package com.piotrkalitka.shoppinglist.ui.listDetailsActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.piotrkalitka.shoppinglist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class TextItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.imgRemove)
    ImageView imgRemove;

    private OnItemRemove onItemRemove;
    private boolean editMode;

    TextItemViewHolder(View itemView, boolean editMode, OnItemRemove onItemRemove) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.onItemRemove = onItemRemove;
        this.editMode = editMode;
    }

    static View getView(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.text_item_view_holder, container, false);
    }

    void bind(String item) {
        txtName.setText(item);

        if (editMode) {
            imgRemove.setOnClickListener(v -> onItemRemove.removeItem(item));
        } else {
            imgRemove.setVisibility(View.GONE);
        }
    }

    interface OnItemRemove {
        void removeItem(String item);
    }

}
