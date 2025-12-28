package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crmmobile.LeadDirectory.Lead;
import com.example.crmmobile.QuoteDirectory.Quote;

import java.util.ArrayList;
import java.util.List;

public class QuoteRepository {
    DBCRMHandler dbHelper;

    public QuoteRepository(Context context){
        dbHelper = new DBCRMHandler(context);
    }

    //Add Lead
    public long addQuote(Quote quote){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TENBAOGIA", quote.getOrderCode());
        values.put("CONGTY", quote.getCompany());
        values.put("NGUOILIENHE", quote.getContactPersonID());
        values.put("COHOI", quote.getOpportunityName());
        values.put("TINHTRANG", quote.getState());
        values.put("SANPHAM", quote.getProduct());
        values.put("SOLUONG", quote.getQuantity());
        values.put("DONGIA", quote.getPrice());
        values.put("TONGTIEN", quote.getTotalAmount());
        values.put("NGAYTAO", quote.getDate());

        long newId = db.insert("BAOGIA", null, values);
        db.close();
        return  newId;
    }

    //get all quote
    public List<Quote> getAllQuote(){
        List<Quote> list = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM BAOGIA", null );

        if(cursor.moveToFirst()){
            do {
                Quote quote = new Quote();
                quote.setID(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                quote.setOrderCode(cursor.getString(cursor.getColumnIndexOrThrow("TENBAOGIA")));
                quote.setCompany(cursor.getString(cursor.getColumnIndexOrThrow("CONGTY")));
                quote.setContactPersonID(cursor.getInt(cursor.getColumnIndexOrThrow("NGUOILIENHE")));
                quote.setOpportunityName(cursor.getString(cursor.getColumnIndexOrThrow("COHOI")));
                quote.setState(cursor.getString(cursor.getColumnIndexOrThrow("TINHTRANG")));
                quote.setProduct(cursor.getString(cursor.getColumnIndexOrThrow("SANPHAM")));
                quote.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("SOLUONG")));
                quote.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow("DONGIA")));
                quote.setTotalAmount(cursor.getLong(cursor.getColumnIndexOrThrow("TONGTIEN")));
                quote.setDate(cursor.getString(cursor.getColumnIndexOrThrow("NGAYTAO")));

                list.add(quote);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    //Update Lead
    public int updateQuote(Quote quote){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TENBAOGIA", quote.getOrderCode());
        values.put("CONGTY", quote.getCompany());
        values.put("NGUOILIENHE", quote.getContactPersonID());
        values.put("COHOI", quote.getOpportunityName());
        values.put("TINHTRANG", quote.getState());
        values.put("SANPHAM", quote.getProduct());
        values.put("SOLUONG", quote.getQuantity());
        values.put("DONGIA", quote.getPrice());
        values.put("TONGTIEN", quote.getTotalAmount());
        values.put("NGAYTAO", quote.getDate());

        int result = db.update("BAOGIA", values, "ID=?", new String[]{String.valueOf(quote.getID())});
        db.close();
        return result;
    }

    //Delete Quote
    public void DeleteQuote(int id){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.delete("BAOGIA", "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Quote getQuoteByID(int id){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Quote quote = null;

        Cursor cursor = db.rawQuery("SELECT * FROM BAOGIA WHERE ID = ?", new String[]{String.valueOf(id)});

        if(cursor.moveToFirst()){
            do {
                quote = new Quote();
                quote.setID(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                quote.setOrderCode(cursor.getString(cursor.getColumnIndexOrThrow("TENBAOGIA")));
                quote.setCompany(cursor.getString(cursor.getColumnIndexOrThrow("CONGTY")));
                quote.setContactPersonID(cursor.getInt(cursor.getColumnIndexOrThrow("NGUOILIENHE")));
                quote.setOpportunityName(cursor.getString(cursor.getColumnIndexOrThrow("COHOI")));
                quote.setState(cursor.getString(cursor.getColumnIndexOrThrow("TINHTRANG")));
                quote.setProduct(cursor.getString(cursor.getColumnIndexOrThrow("SANPHAM")));
                quote.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("SOLUONG")));
                quote.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow("DONGIA")));
                quote.setTotalAmount(cursor.getLong(cursor.getColumnIndexOrThrow("TONGTIEN")));
                quote.setDate(cursor.getString(cursor.getColumnIndexOrThrow("NGAYTAO")));

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return quote;
    }
}
