package com.example.mylenovo.testapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Purchased_ItemsDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="MyDatabase_Purchased_Transaction.db";
    private static final String TABLE_NAME="SellingTransactionTable";
    private static final String ID="_TID";
    private static final String DATE="TRANSACTIONDATE";
    private static final String PURCHASED="PURCHASEDITEMS";
    private static final String SELLER="SELLER";
    private static final int VERSION=2;

    Context context;

    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DATE+" INTEGER NOT NULL, "+PURCHASED+" VARCHAR(50) NOT NULL, "+SELLER+" VARCHAR(50) NOT NULL);";

    public Purchased_ItemsDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertData(String date, String purchased, String seller){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DATE, date);
        contentValues.put(PURCHASED, purchased);
        contentValues.put(SELLER, seller);

        long row = sq.insert(TABLE_NAME, null, contentValues);

        System.out.println("Jldkkdksd MAhamudul kfkdlfs sdfkjs nnvnvnnv   "+row);

        return row;
    }

    public Cursor readAll(){
        String SELECT_ALL="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase sql = this.getReadableDatabase();
        return sql.rawQuery(SELECT_ALL, null);
    }
}
