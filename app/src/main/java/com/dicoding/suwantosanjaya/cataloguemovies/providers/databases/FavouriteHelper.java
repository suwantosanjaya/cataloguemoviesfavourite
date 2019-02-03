package com.dicoding.suwantosanjaya.cataloguemovies.providers.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.dicoding.suwantosanjaya.cataloguemovies.providers.models.FavouriteModel;

import java.util.ArrayList;

/**
 * Created by Suwanto Sanjaya on 17/12/2018.
 */
public class FavouriteHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public FavouriteHelper(Context context) {
        this.context = context;
    }

    public FavouriteHelper() {
    }

    public FavouriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public ArrayList<FavouriteModel> getDataAll() {
        Cursor cursor = database.query(DatabaseContract.TABLE_FAVOURITE, null,
                null, null,
                null, null, DatabaseContract.FavouriteColumns.ID + " ASC", null);

        cursor.moveToFirst();
        ArrayList<FavouriteModel> arrayList = new ArrayList<>();
        FavouriteModel favouriteModel;

        if (cursor.getCount() > 0) {

            do {
                favouriteModel = new FavouriteModel();
                favouriteModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.ID)));
                favouriteModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TITLE)));
                favouriteModel.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POSTER)));
                favouriteModel.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.OVERVIEW)));
                favouriteModel.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.RELEASE_DATE)));
                favouriteModel.setVoteAverage(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.VOTE_AVERAGE)));
                arrayList.add(favouriteModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public int countMovie(int id) {
        int count;
        Cursor cursor = database.query(DatabaseContract.TABLE_FAVOURITE, null,
                DatabaseContract.FavouriteColumns.ID + " = ?", new String[]{id + ""},
                null, null, DatabaseContract.FavouriteColumns.ID + " ASC", null);

        cursor.moveToFirst();
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public void insertTransaction(FavouriteModel favouriteModel) {
        String sql = "INSERT INTO " + DatabaseContract.TABLE_FAVOURITE + " (" +
                DatabaseContract.FavouriteColumns.ID + ", " +
                DatabaseContract.FavouriteColumns.TITLE + ", " +
                DatabaseContract.FavouriteColumns.POSTER + ", " +
                DatabaseContract.FavouriteColumns.OVERVIEW + ", " +
                DatabaseContract.FavouriteColumns.RELEASE_DATE + ", " +
                DatabaseContract.FavouriteColumns.VOTE_AVERAGE +
                ") VALUES (?, ?, ?, ?, ?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindLong(1, favouriteModel.getId());
        stmt.bindString(2, favouriteModel.getTitle());
        stmt.bindString(3, favouriteModel.getPoster());
        stmt.bindString(4, favouriteModel.getOverview());
        stmt.bindString(5, favouriteModel.getReleaseDate());
        stmt.bindDouble(6, favouriteModel.getVoteAverage());
        stmt.execute();
        stmt.clearBindings();
    }

    public int update(FavouriteModel favouriteModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseContract.FavouriteColumns.TITLE, favouriteModel.getTitle());
        initialValues.put(DatabaseContract.FavouriteColumns.POSTER, favouriteModel.getPoster());
        initialValues.put(DatabaseContract.FavouriteColumns.OVERVIEW, favouriteModel.getOverview());
        initialValues.put(DatabaseContract.FavouriteColumns.RELEASE_DATE, favouriteModel.getReleaseDate());
        initialValues.put(DatabaseContract.FavouriteColumns.VOTE_AVERAGE, favouriteModel.getVoteAverage());
        return database.update(DatabaseContract.TABLE_FAVOURITE, initialValues,
                DatabaseContract.FavouriteColumns.ID + "='" + favouriteModel.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(DatabaseContract.TABLE_FAVOURITE,
                DatabaseContract.FavouriteColumns.ID + " = '" + id + "'", null);
    }


    public Cursor queryByIdProvider(String id) {
        return database.query(DatabaseContract.TABLE_FAVOURITE, null,
                DatabaseContract.FavouriteColumns.ID + " = ?",
                new String[]{id}, null, null, null, null);
    }

    public Cursor queryProvider() {
        return database.query(DatabaseContract.TABLE_FAVOURITE, null, null,
                null, null, null,
                DatabaseContract.FavouriteColumns.ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DatabaseContract.TABLE_FAVOURITE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DatabaseContract.TABLE_FAVOURITE, values,
                DatabaseContract.FavouriteColumns.ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DatabaseContract.TABLE_FAVOURITE,
                DatabaseContract.FavouriteColumns.ID + " = ?", new String[]{id});
    }
}
