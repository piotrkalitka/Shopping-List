package com.piotrkalitka.shoppinglist.ui.mainActivity.fragments.archivedListsFragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.piotrkalitka.shoppinglist.R;
import com.piotrkalitka.shoppinglist.ui.mainActivity.fragments.RecyclerViewFragment;

public class ArchivedListsFragment extends RecyclerViewFragment {

    @Override
    protected boolean showArchivedLists() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archived_lists, container, false);
    }
}
