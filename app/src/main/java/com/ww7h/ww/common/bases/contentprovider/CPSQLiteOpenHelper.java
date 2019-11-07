package com.ww7h.ww.common.bases.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;
import com.ww7h.ww.common.R;

/**
 * Created by: Android Studio.
 * Project Nam: Android-common
 * PackageName: com.ww7h.ww.common.bases.contentprovider
 * DateTime: 2019/3/29 20:24
 *
 * @author ww
 */
public class CPSQLiteOpenHelper {

    /**
     * 数据库操作
     */
    private SQLiteOpenHelper sqLiteOpenHelper;

    private static class CPSQLiteOpenHelperInstance {
        private final static CPSQLiteOpenHelper INSTANCE;
        static {
            INSTANCE = new CPSQLiteOpenHelper();
        }
    }

    public static CPSQLiteOpenHelper getInstance() {
        return CPSQLiteOpenHelperInstance.INSTANCE;
    }

    public void init(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        if (sqLiteOpenHelper == null) {
            synchronized (CPSQLiteOpenHelper.class) {
                if (sqLiteOpenHelper == null) {
                    sqLiteOpenHelper = new SQLiteOpenHelper(context, name, factory, version);
                }
            }
        }
    }

    private class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

        private Context mContext;

        SQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.beginTransaction();
                String[] sqlArray = mContext.getResources().getStringArray(R.array.create_table_sql);
                for (String sql : sqlArray) {
                    db.execSQL(sql);
                }
            } catch(Exception e){
                e.printStackTrace();
            } finally {
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion != newVersion) {
                try{
                    db.beginTransaction();
                    String[] sqlArray = mContext.getResources().getStringArray(R.array.update_table_sql);
                    for (String sql : sqlArray) {
                        db.execSQL(sql);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                } finally {
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

}
