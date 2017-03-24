package poojab26.niki;

/**
 * Created by pblead26 on 24-Mar-17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "chatManager";

    // Contacts table name
    private static final String TABLE_NAME = "messages";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USER = "user";
    private static final String KEY_MESSAGE = "message";
    private static final String[] Table_AllKeys = new String[] { KEY_USER, KEY_MESSAGE };


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_USER + " TEXT, " + KEY_MESSAGE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    public boolean insertData(String user, String mesg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER,user);
        contentValues.put(KEY_MESSAGE,mesg);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }

    public Cursor getRowData(String subid) {
        SQLiteDatabase db = this.getWritableDatabase();

        // String WHERE = KEY_SUBID + "=?";
     /*   Cursor cursor = db.query(TABLE_NAME, Table_AllKeys,
                WHERE, new String[] { subid },
                null, null, null);*/
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_USER + "='" + subid.trim()+"'";
        Cursor res = db.rawQuery(query, null);



        return res;
    }


    public Cursor getPercData(String subid) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT perc FROM " + TABLE_NAME + " WHERE " + KEY_USER + "='" + subid.trim()+"'";
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public boolean updateData(String user, String mesg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(KEY_ID,id);
        contentValues.put(KEY_USER,user);
        contentValues.put(KEY_MESSAGE,mesg);

        db.update(TABLE_NAME, contentValues, "USER = ?",new String[] { user });
        return true;
    }
}