package com.example.sqliteandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqliteandroid.Model.CustomerModel;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {


    final static String customer_table = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String createSqlTable = "CREATE TABLE " + customer_table + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CUSTOMER_NAME + " TEXT," + COLUMN_CUSTOMER_AGE + " INT, " + COLUMN_ACTIVE_CUSTOMER + " BOOL)";
        sqLiteDatabase.execSQL(createSqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //used for test purposes
    public boolean addOneColumn(CustomerModel customerModel){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //associative array like thing for bringing all the data
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUSTOMER_NAME,customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE,customerModel.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER,customerModel.isActive());
        //nullCOlumHack is used to put atleast one value in the row
        final long insert = sqLiteDatabase.insert(customer_table, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteOne(CustomerModel customerModel){
   //if a customer is found then we will delete that with id and return true oher wise false
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        String queryString = "DELETE FROM "+customer_table +" WHERE "+ " "+COLUMN_ID +" "+"="+customerModel.getId();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return  false;
        }

    }
    public List<CustomerModel> getAllList(){
        List<CustomerModel>returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+customer_table;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            do{

                int customerID =cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive =cursor.getInt(3) == 1 ? true:false;
                CustomerModel customerModel = new CustomerModel(customerID,customerName,customerAge,customerActive);
                returnList.add(customerModel);

            }while(cursor.moveToNext());
        } else{

        }
        //close them when the data is passed in the returnList
        cursor.close();
        sqLiteDatabase.close();
        return  returnList;
    }
}
