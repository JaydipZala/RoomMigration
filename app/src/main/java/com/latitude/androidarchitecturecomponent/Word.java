package com.latitude.androidarchitecturecomponent;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by jaydipsinh on 28/7/18.
 **/
@Entity(tableName = DBConstants.TABLE_WORD)
public class Word {

    public static void upgradeWordTable(SupportSQLiteDatabase database) {

        ArrayList<String> columns = DBUtils.getColumns(database, DBConstants.TABLE_WORD);

        // 1. Create new table
        final String TABLE_NAME_TEMP = "_temp" + DBConstants.TABLE_WORD;
        database.execSQL("CREATE TABLE IF NOT EXISTS `" + TABLE_NAME_TEMP + "` (`" + DBConstants.KEY_WORD + "` TEXT NOT NULL, `" + DBConstants.KEY_RANDOM_NUMBER + "` TEXT, PRIMARY KEY(`" + DBConstants.KEY_WORD + "`))");

        // 2. find common columns.
        columns.retainAll(DBUtils.getColumns(database, TABLE_NAME_TEMP));
        final String COLS = TextUtils.join(",", columns);

        // 3. Copy the data
        database.execSQL("INSERT INTO " + TABLE_NAME_TEMP + " (" + COLS + ") "
                + "SELECT " + COLS + " "
                + "FROM " + DBConstants.TABLE_WORD);

        // 4. Remove the old table
        database.execSQL("DROP TABLE " + DBConstants.TABLE_WORD);

        // 5. Change the table name to the correct one
        database.execSQL("ALTER TABLE " + TABLE_NAME_TEMP + " RENAME TO " + DBConstants.TABLE_WORD);
    }


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = DBConstants.KEY_WORD)
    private String mWord;

    @Nullable
    @ColumnInfo(name = DBConstants.KEY_RANDOM_NUMBER)
    private String mRandomNumber;

    public Word(@NonNull String word, @Nullable String mRandomNumber) {
        this.mWord = word;
        this.mRandomNumber = mRandomNumber;
    }

    public String getWord() {
        return this.mWord;
    }

    @NonNull
    public String getRandomNumber() {
        return mRandomNumber == null ? "" : mRandomNumber;
    }
}
