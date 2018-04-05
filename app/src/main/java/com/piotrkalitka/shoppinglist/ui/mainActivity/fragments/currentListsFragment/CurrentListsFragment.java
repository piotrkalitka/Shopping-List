package com.piotrkalitka.shoppinglist.ui.mainActivity.fragments.currentListsFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.piotrkalitka.shoppinglist.Constants;
import com.piotrkalitka.shoppinglist.R;
import com.piotrkalitka.shoppinglist.db.DbHelper;
import com.piotrkalitka.shoppinglist.ui.listDetailsActivity.ListDetailsActivity;
import com.piotrkalitka.shoppinglist.ui.mainActivity.fragments.RecyclerViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentListsFragment extends RecyclerViewFragment {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_lists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initFabListener();
    }

    @Override
    protected boolean showArchivedLists() {
        return false;
    }

    private void initFabListener() {
        fab.setOnClickListener(v -> showListCreatingDialog());
    }

    private void showListCreatingDialog() {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_list);
        dialog.setCancelable(true);

        EditText fldTitle = dialog.findViewById(R.id.fldTitle);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        fldTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fldTitle.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnSubmit.setOnClickListener(v -> onDialogSubmitted(dialog, fldTitle));
        dialog.show();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(dialog.getWindow().getAttributes());
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
    }

    private void onDialogSubmitted(Dialog dialog, EditText fldTitle) {
        if (TextUtils.isEmpty(fldTitle.getText().toString())) {
            fldTitle.setError(getString(R.string.dialog_add_list_title_empty_error));
            return;
        }
        dialog.dismiss();

        int id = (int) DbHelper.getInstance(getContext()).addList(fldTitle.getText().toString().trim());
        updateRecyclerView();
        Intent intent = new Intent(getContext(), ListDetailsActivity.class);
        intent.putExtra(Constants.BUNDLE_LIST_ID, id);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
    }
}
