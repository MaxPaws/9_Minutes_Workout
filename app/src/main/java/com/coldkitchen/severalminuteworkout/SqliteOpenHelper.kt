package com.coldkitchen.severalminuteworkout

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ru.cleverpumpkin.calendar.CalendarDate
import java.security.AccessControlContext
import java.sql.Date
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


//Constants with the version of db, names of tables, columns, and primary key;
//Methods for create/upgrade/delete tables

class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {

    companion object{
        private val DB_VERSION = 1
        private val DB_NAME = "Workout.db"
        private val TABLE_HISTORY = "history"
        private val COLUMN_ID = "_id"
        private val COLUMN_COMPLETED_DATE = "completed_date"
    }

    //Procedure for SQLite with creating database with given constants-names, exec of this procedure, in
    // override onCreate method.
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE "
                + TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_COMPLETED_DATE + " TEXT" + ")")
        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    //Override onUpgrade method that drops table and start-over onCreate method, needs to call when
    // we update the table with new columns, etc. (version needs to be updated either).
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)
        onCreate(db)
    }

    //Write a functions to insert data in db and to read it from db.
    fun addDate(date: String){
        //Values that we need to add
        val values = ContentValues()
        //Add func argument to a completed date column
        values.put(COLUMN_COMPLETED_DATE, date)
        //Add values to my DB
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY, null, values)
        db.close()
    }

    fun getAllCompletedDatesList(): ArrayList<String>{
        val list = ArrayList<String>()
        val db = this.readableDatabase
        //Cursor is like a scanner for each row
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)
        while (cursor.moveToNext()){
            //Get the value from current row's column named Completed date
            val dateValue = (cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))
            list.add(dateValue)
        }
        cursor.close()

        return list
    }

    fun getAllCompletedDatesListCalendarDates(): ArrayList<CalendarDate>{
        val list = ArrayList<CalendarDate>()
        val db = this.readableDatabase
        //Cursor is like a scanner for each row
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)
        while (cursor.moveToNext()){
            //Get the value from current row's column named Completed date
            val dateValue = (cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))

            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val formattedDate = sdf.parse(dateValue)
            list.add(CalendarDate(formattedDate!!))
        }
        cursor.close()

        return list
    }

}