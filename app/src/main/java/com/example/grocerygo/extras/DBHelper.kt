package com.example.grocerygo.extras

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper :
    SQLiteOpenHelper(App.instance, Config.DATABASE_NAME, null, Config.DATABASE_VERSION) {
    val db = writableDatabase

    companion object {
        const val TABLE_NAME = "Products"
        const val COL_NAME = "Name"
        const val COL_AMOUNT = "Amount"
        const val COL_ID = "ID"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        logz("DBHelper`onCreate")
        val sqlCreateTable =
            "create table $TABLE_NAME($COL_NAME char(50), $COL_AMOUNT char(3), $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT)"
        db?.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        logz("DBHelper`onUpgrade")
        val sqlDropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(sqlDropTable)
    }

    fun addProduct(name: String, amount: String) {
        logz("DBHelper`addProduct. name:$name amount:$amount")
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_AMOUNT, amount)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun deleteProduct(id: Int) {
        val whereClause = "$COL_ID=?"
        val whereArgs = arrayOf(id.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
    }

}