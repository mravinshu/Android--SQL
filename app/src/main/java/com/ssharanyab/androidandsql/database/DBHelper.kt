package com.hs1121.deligetsexample.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "MyDatabase", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME.toString() + " ("
                + ID_COL.toString() + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL.toString() + " TEXT,"
                + AGE_COL.toString() + " INTEGER"
                + ")")

        db?.execSQL(query)
    }

    fun addUser(
        name: String?,
        age: Int?,
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(AGE_COL, age)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getUser(): ArrayList<UserModel> {

        val db = this.readableDatabase

        val cursorCourses: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val courseModalArrayList: ArrayList<UserModel> = ArrayList()

        if (cursorCourses.moveToFirst()) {
            do {
                courseModalArrayList.add(
                    UserModel(
                        cursorCourses.getString(0).toInt(),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2).toInt()
                    )
                )
            } while (cursorCourses.moveToNext())

        }

        cursorCourses.close()
        return courseModalArrayList
    }

    fun deleteUser(id: Int) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME,"id =?", arrayOf(id.toString()))
        db.close()
    }


    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME");
        onCreate(db)
    }

    companion object {
        const val TABLE_NAME = "sqltable"
        const val ID_COL = "id"
        const val NAME_COL = "name"
        const val AGE_COL = "age"

    }

}