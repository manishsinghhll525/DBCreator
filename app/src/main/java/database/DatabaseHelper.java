package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by techelogy2 on 7/3/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "suburbsDB";
    private static final String TABLE_NAME = "suburbsTable";
    private static int DATABASE_VERSION = 1;


    /**
     * table column keys goes here
     */


    private static final String KEY_ID = "id";
    private static final String KEY_SUBURBS = "suburbs";
    private static final String KEY_STATE_ID = "state_id";
    private static final String KEY_POST_CODE = "post_code";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_COUNTRY_NAME = "country_name";
    private static final String KEY_DISPLAY_TITLE = "display_title";
    private static final String KEY_COUNTRY_ID = "country_id";
    private static final String KEY_DELETE_STATUS = "delete_status";
    private static final String KEY_INSERTION_DATETIME = "insertion_datetime";

    private static final String DATATYPE_TEXT = " TEXT, ";

    Context context;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        System.out.println("inside DatabaseHelper = " + context);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("inside onCreate = ");
        String createQuery = "CREATE TABLE " + TABLE_NAME + " ( "
                + KEY_ID + DATATYPE_TEXT
                + KEY_SUBURBS + DATATYPE_TEXT
                + KEY_STATE_ID + DATATYPE_TEXT
                + KEY_POST_CODE + DATATYPE_TEXT
                + KEY_LAT + DATATYPE_TEXT
                + KEY_LNG + DATATYPE_TEXT
                + KEY_COUNTRY_ID + DATATYPE_TEXT
                + KEY_DELETE_STATUS + DATATYPE_TEXT
                + KEY_COUNTRY_NAME + DATATYPE_TEXT
                + KEY_DISPLAY_TITLE + DATATYPE_TEXT
                + KEY_INSERTION_DATETIME + " TEXT " + ")";
        db.execSQL(createQuery);

        System.out.println("database table created successfully = " + createQuery);


    }


    public void insertBulkRecordsIntoDatabase(ArrayList<SuburbModel> suburbList) {


        if (isDatabaseExist()) {

        } else {

            SQLiteDatabase database = getWritableDatabase();

            database.beginTransaction();

            try {
                ContentValues values = new ContentValues();
                for (int i = 0; i < suburbList.size(); i++) {
                    SuburbModel model = suburbList.get(i);
                    values.put(KEY_ID, model.getId());
                    values.put(KEY_SUBURBS, model.getSuburbs());
                    values.put(KEY_STATE_ID, model.getState_id());
                    values.put(KEY_POST_CODE, model.getPost_code());
                    values.put(KEY_LAT, model.getLat());
                    values.put(KEY_LNG, model.getLng());
                    values.put(KEY_COUNTRY_ID, model.getCountry_id());
                    values.put(KEY_DELETE_STATUS, model.getDelete_status());
                    values.put(KEY_COUNTRY_NAME, model.getCountry_name());
                    values.put(KEY_DISPLAY_TITLE, model.getDisplay_title());
                    values.put(KEY_INSERTION_DATETIME, model.getInsertionTime());
                    database.insert(TABLE_NAME, null, values);
                    System.out.println("record inserted  = " + i);
                }
                database.setTransactionSuccessful();

            } finally {
                database.endTransaction();
            }

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public Cursor searchForMatchingSuburbEnteries(String suburb) {

        suburb = /*"%" + */suburb + "%";
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_SUBURBS + " like '" + suburb + "' LIMIT 30";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);


        return cursor;
    }


    public Cursor searchForOtherMatchingSuburbEnteries(String suburb) {

        suburb = "%" + suburb + "%";
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_SUBURBS + " like '" + suburb + "' LIMIT 30";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);


        return cursor;
    }

    public boolean isDatabaseExist() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        System.out.println("dbFile.exists() = " + dbFile.exists());
        return dbFile.exists();
    }
}
