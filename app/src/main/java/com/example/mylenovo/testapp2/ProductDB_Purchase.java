package com.example.mylenovo.testapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ProductDB_Purchase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="MyDatabase_Purchasing_Product.db";
    private static final String TABLE_NAME="ProductTable";
    private static final String ID="_PID";
    private static final String NAME="Product_Name";
    private static final String TYPE="Product_Type";
    private static final String PRICE_UNIT="Price_Unit";
    private static final int VERSION=2;

    Context context;

    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NAME+" INTEGER NOT NULL, "+TYPE+" VARCHAR(50) NOT NULL, "+PRICE_UNIT+" VARCHAR(50) NOT NULL);";


    public ProductDB_Purchase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            //System.out.println("Jldkkdksd MAhamudul hasan Rafi");
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "Table is created", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*Toast.makeText(context, "Table is updated", Toast.LENGTH_SHORT).show();
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);*/
    }

    public long insertData(String name, String type, String p_u){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, name);
        contentValues.put(TYPE, type);
        contentValues.put(PRICE_UNIT, p_u);

        long row = sq.insert(TABLE_NAME, null, contentValues);

        System.out.println("Jldkkdksd MAhamudul kfkdlfs sdfkjsfh sdfhhhhhhh   "+row);

        return row;
    }

    /*public Cursor readData(String phn){
        String SELECT_TABLE="SELECT * FROM "+TABLE_NAME+" WHERE "+PHONE+" = '"+phn+"'";
        System.out.print(SELECT_TABLE+"\n");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(SELECT_TABLE, null);
    }*/

    public Cursor readAll(){
        String SELECT_ALL="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase sql = this.getReadableDatabase();
        return sql.rawQuery(SELECT_ALL, null);
    }

    public int deleteRecord(String product_name){
        SQLiteDatabase sq = this.getWritableDatabase();
        return sq.delete(TABLE_NAME, NAME+"=?", new String[]{product_name});
    }

    public void deleteAll(){
        String DELETE_ALL = "DELETE FROM "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(DELETE_ALL);
        return;
    }
}
