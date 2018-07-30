package com.latitude.androidarchitecturecomponent;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jaydipsinh on 30/7/18.
 **/
public class DBUtils {
    public static ArrayList<String> getColumns(SupportSQLiteDatabase db, String tableName) {
        ArrayList<String> al = null;
        Cursor c = null;
        try {
            c = db.query("SELECT * FROM " + tableName + " LIMIT 1", null);
            if (c != null) {
                al = new ArrayList<>(Arrays.asList(c.getColumnNames()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }
        return al;
    }
}
