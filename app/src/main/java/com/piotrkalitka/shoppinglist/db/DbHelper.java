package com.piotrkalitka.shoppinglist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.piotrkalitka.shoppinglist.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DbHelper extends SQLiteOpenHelper {

    private static DbHelper dbHelper;
    private static final String[] LISTS_COLS = new String[]{"id", "title", "items", "archived", "timestamp"};

    public static DbHelper getInstance(Context context) {
        if (dbHelper == null) dbHelper = new DbHelper(context);
        return dbHelper;
    }

    private DbHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createListsTable =
                "CREATE TABLE 'lists' " +
                        "(" +
                        "'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "'title' VARCHAR, " +
                        "'items' VARCHAR, " +
                        "'archived' INTEGER DEFAULT 0, " +
                        "'timestamp' VARCHAR" +
                        ")";
        sqLiteDatabase.execSQL(createListsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @SuppressWarnings("unchecked")
    public List<ListModel> getLists(boolean archived) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        List<ListModel> lists = new ArrayList<>();
        int isArchived = archived ? 1 : 0;

        try (Cursor listCursor = readableDatabase.query(
                "lists",
                LISTS_COLS,
                "archived = " + isArchived,
                null,
                null,
                null,
                null)) {
            while (listCursor.moveToNext()) {
                ListModel list = new ListModel(
                        listCursor.getInt(0),
                        listCursor.getString(1),
                        new Gson().fromJson(listCursor.getString(2), ArrayList.class),
                        listCursor.getInt(3) == 1,
                        listCursor.getString(4));
                lists.add(list);
            }
        }
        return lists;
    }

    @SuppressWarnings("unchecked")
    public ListModel getList(int id) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ListModel list = null;

        try (Cursor listCursor = readableDatabase.query(
                "lists",
                LISTS_COLS,
                "id = " + id,
                null,
                null,
                null,
                null
        )) {
            while (listCursor.moveToNext()) {
                list = new ListModel(
                        listCursor.getInt(0),
                        listCursor.getString(1),
                        new Gson().fromJson(listCursor.getString(2), ArrayList.class),
                        listCursor.getInt(3) == 1,
                        listCursor.getString(4));
            }
        }
        return list;
    }

    public long addList(String title) {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("timestamp", getTimestamp());

        return writableDatabase.insert("lists", null, cv);
    }

    public void archiveList(int id) {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("archived", 1);

        writableDatabase.update("lists", cv, "id = " + id, null);
    }

    public void addItem(int listId, String item) {
        ListModel listModel = getList(listId);
        List<String> items = listModel.getItems();
        if (items == null) items = new ArrayList<>();
        items.add(item);
        saveItemsToList(listId, items);
    }

    public void removeItem(int listId, String item) {
        ListModel listModel = getList(listId);
        List<String> items = listModel.getItems();
        items.remove(item);
        saveItemsToList(listId, items);
    }

    private void saveItemsToList(int listId, List<String> items) {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("items", new Gson().toJson(items));

        writableDatabase.update("lists", cv, "id = " + listId, null);
    }


    private static String getTimestamp() {
        SimpleDateFormat timestampSdf = new SimpleDateFormat(Constants.TIMESTAMP_SDF, Locale.getDefault());
        return timestampSdf.format(new Date());
    }
}