package com.example.mylenovo.testapp2;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class UserDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="MyDatabase.db";
    private static final String TABLE_NAME="User_Info";
    private static final String ID="_ID";
    private static final String USERNAME="Username";
    private static final String PHONE="Phone";
    private static final String EMAIL="Email";
    private static final String PASSWORD="Password";
    private static final String HOUSE="House_No";
    private static final String ROAD="Road_No";
    private static final String AREA="Area";
    private static final String SECTOR="Sector";
    private static final int VERSION=1;

    Context context;

    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            USERNAME+" VARCHAR(50) NOT NULL, "+PHONE+" VARCHAR(50) NOT NULL, "+EMAIL+" VARCHAR(50), "+PASSWORD+" VARCHAR(50) NOT NULL, "+
            HOUSE+" VARCHAR(50) NOT NULL, "+ROAD+" VARCHAR(50) NOT NULL, "+AREA+" VARCHAR(50) NOT NULL, "+SECTOR+" VARCHAR(50));";


    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "Table is created", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String username, String phone, String email, String password, String house,
                           String road, String area, String sector){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USERNAME, username);
        contentValues.put(PHONE, phone);
        contentValues.put(EMAIL, email);
        contentValues.put(PASSWORD, password);
        contentValues.put(HOUSE, house);
        contentValues.put(ROAD, road);
        contentValues.put(AREA, area);
        contentValues.put(SECTOR, sector);

        long row = sq.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor readData(String phn){
        String SELECT_TABLE="SELECT * FROM "+TABLE_NAME+" WHERE "+PHONE+" = '"+phn+"'";
        System.out.print(SELECT_TABLE+"\n");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(SELECT_TABLE, null);
    }

    public Cursor readAll(String SELECT_ALL){
        SQLiteDatabase sql = this.getReadableDatabase();
        return sql.rawQuery(SELECT_ALL, null);
    }

    public int updateTB(String un, String upn, String ueml, String upass, String phone){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, un);
        contentValues.put(PHONE, upn);
        contentValues.put(EMAIL, ueml);
        contentValues.put(PASSWORD, upass);

        return sqLiteDatabase.update(TABLE_NAME, contentValues, PHONE+"=?",new String[]{phone});
    }

    public int updateTB2(String house, String road, String block, String area, String phone){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HOUSE, house);
        contentValues.put(ROAD, road);
        contentValues.put(SECTOR, block);
        contentValues.put(AREA, area);

        return sqLiteDatabase.update(TABLE_NAME, contentValues, PHONE+"=?",new String[]{phone});
    }
}
