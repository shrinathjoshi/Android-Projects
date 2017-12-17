package com.example.hp.studentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Hp on 4/16/2017.
 */

public class DbHandler extends SQLiteOpenHelper{

    Context context;
    SQLiteDatabase db;


    DbHandler(Context context)
    {
        super(context,"studentdb",null,1);
        this.context=context;
        db=this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String c="Create table student (rno integer primary key, name text)";
        db.execSQL(c);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


    public void  addStudent(int rno, String name)
    {
        ContentValues cv=new ContentValues();
        cv.put("rno",rno);
        cv.put("name",name);

        long rowid=db.insert("student",null,cv);

        if(rowid<0)
        {
            Toast.makeText(context, "Insert Issue", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Insert Success", Toast.LENGTH_SHORT).show();
        }
    }


    public  void updateStudent(int rno,String name)
    {
        ContentValues cv=new ContentValues();
        cv.put("rno",rno);
        cv.put("name",name);

        long rowid=db.update("student",cv,"rno="+rno,null);

        if(rowid==0)
        {
            Toast.makeText(context, "Record doesn't exist", Toast.LENGTH_SHORT).show();
        }
        if(rowid<0)
        {
            Toast.makeText(context, "Update Issue", Toast.LENGTH_SHORT).show();
        }
        else if(rowid>=1)
        {
            Toast.makeText(context, "Update Success", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteStudent(int rno)
    {

        long rowid=db.delete("student","rno="+rno,null);

        if(rowid==0)
        {
            Toast.makeText(context, "Record doesn't exist", Toast.LENGTH_SHORT).show();
        }
        if(rowid<0)
        {
            Toast.makeText(context, "Delete Issue", Toast.LENGTH_SHORT).show();
        }
        else if(rowid >=1)
        {
            Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
        }

    }


    public String viewStudent()
    {
        StringBuffer sb=new StringBuffer();
        Cursor cursor=db.query("student",null,null,null,null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do {
                String rno=cursor.getString(0);
                String name=cursor.getString(1);
                sb.append(" Roll No : "+rno + " Name : "+name + "\n");
            }while(cursor.moveToNext());
        }
        else
        {
            sb.append("no record to show");
        }
            return sb.toString();
    }



}
