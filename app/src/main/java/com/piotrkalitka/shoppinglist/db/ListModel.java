package com.piotrkalitka.shoppinglist.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.piotrkalitka.shoppinglist.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListModel implements Comparable<ListModel> {
    private int id;
    private String title;
    private List<String> items;
    private boolean archived;
    private String timestamp;

    ListModel(int id, String title, List<String> items, boolean archived, String timestamp) {
        this.id = id;
        this.title = title;
        this.items = items;
        this.archived = archived;
        this.timestamp = timestamp;
        if (this.items == null) this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getItems() {
        return items;
    }

    public boolean isArchived() {
        return archived;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Nullable
    public Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.TIMESTAMP_SDF, Locale.getDefault());
        Date date;
        try {
            date = sdf.parse(timestamp);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    @Override
    public int compareTo(@NonNull ListModel listModel) {
        if (getDate() == null) return 0;
        return getDate().compareTo(listModel.getDate());
    }
}
