package be.melyuki.roadtripapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context : Context) : SQLiteOpenHelper(context, DbContract.NAME, null, DbContract.VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbContract.FavTable.SCRIPT_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // !!! PAS EN PROD !!!
        db?.execSQL(DbContract.FavTable.SCRIPT_DROP)
        onCreate(db)
    }
}