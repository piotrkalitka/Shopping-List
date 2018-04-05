package com.piotrkalitka.shoppinglist.ui.listDetailsActivity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.piotrkalitka.shoppinglist.Constants;
import com.piotrkalitka.shoppinglist.R;
import com.piotrkalitka.shoppinglist.db.DbHelper;
import com.piotrkalitka.shoppinglist.db.ListModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDetailsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ListModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        ButterKnife.bind(this);

        prepareListModel();
        setTitle();
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!model.isArchived()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list_options, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_archive:
                showArchiveListDialog();
                break;
        }
        return true;
    }

    private void setTitle() {
        if (model.isArchived()) {
            setTitle(getString(R.string.list_archived_title, model.getTitle()));
        } else {
            setTitle(model.getTitle());
        }
    }

    private void prepareListModel() {
        int listId = getIntent().getIntExtra(Constants.BUNDLE_LIST_ID, -1);
        model = DbHelper.getInstance(this).getList(listId);
    }

    private void showArchiveListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.archive_list_message);
        builder.setPositiveButton(R.string.archive_list_positive_btn, (dialogInterface, i) -> {
            DbHelper.getInstance(this).archiveList(model.getId());
            finish();
        });
        builder.setNegativeButton(R.string.archive_list_negative_btn, (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.create().show();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        updateRecyclerView();
    }

    private void updateRecyclerView() {
        ItemsListAdapter adapter = new ItemsListAdapter(this, model.getId(), model.getItems(), !model.isArchived());
        recyclerView.setAdapter(adapter);

    }

}
