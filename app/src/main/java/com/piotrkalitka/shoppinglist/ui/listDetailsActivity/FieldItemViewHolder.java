package com.piotrkalitka.shoppinglist.ui.listDetailsActivity;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.piotrkalitka.shoppinglist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class FieldItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fldAddItem)
    EditText fldAddItem;

    private OnItemAdd onItemAdd;

    FieldItemViewHolder(View itemView, OnItemAdd onItemAdd) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.onItemAdd = onItemAdd;
    }

    static View getView(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.field_item_view_holder, container, false);
    }

    void bind() {
        fldAddItem.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (!TextUtils.isEmpty(fldAddItem.getText().toString().trim())) {
                onItemAdd.addItem(fldAddItem.getText().toString().trim());
                fldAddItem.setText(null);
                return true;
            } else return false;
        });
        fldAddItem.requestFocus();
    }

    interface OnItemAdd {
        void addItem(String item);
    }

}
