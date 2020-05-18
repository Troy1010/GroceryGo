package com.example.grocerygo.extras

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.grocerygo.models.Product

class DBConnection :
    SQLiteOpenHelper(App.instance, Config.DATABASE_NAME, null, Config.DATABASE_VERSION) {
    private val databaseSQL = writableDatabase!!

    companion object {
        const val TABLE_NAME = "Products"
        const val COL_NAME = "Name"
        const val COL_ID = "ID"
    }

    override fun onCreate(sqlDatabase:SQLiteDatabase) {
        logz("DBHelper`onCreate")
        val sqlCreateTable =
            """create table $TABLE_NAME($COL_NAME char(50), 
                   $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT
                   )"""
        sqlDatabase.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(sqlDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        logz("DBHelper`onUpgrade")
        val sqlDropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        sqlDatabase.execSQL(sqlDropTable)
    }

    fun add(product: Product) {
        logz("DBHelper`addProduct. $product")
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, product.productName)
        databaseSQL.insert(TABLE_NAME, null, contentValues)
    }

    fun deleteProduct(id: Int) {
        val whereClause = "$COL_ID=?"
        val whereArgs = arrayOf(id.toString())
        databaseSQL.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun deleteProductByIndex(i:Int) {
        val products = getProducts()
        if (products.hasKey(i)) {
            val id = products[i].sqlID
            deleteProduct(id)
        }
    }

    fun getProducts():ArrayList<Product> {
        val returningList = ArrayList<Product>()
        val columns = arrayOf(COL_ID, COL_NAME)
        val cursor = databaseSQL.query(TABLE_NAME, columns, null, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                returningList.add(Product(
                    productName = cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    sqlID = cursor.getInt(cursor.getColumnIndex(COL_ID))
                ))
            } while(cursor.moveToNext())
        }
        cursor.close()
        return returningList
    }

    fun updateProduct(product: Product) {
        if (product.sqlID == 0) {
            logz("Tried to use updateProduct on a product without a sqlID")
            return
        }
        val whereClause = "$COL_ID=?"
        val whereArgs = arrayOf(product.sqlID.toString())
        var contentValues = ContentValues()
        contentValues.put(COL_NAME,product.productName)
        databaseSQL.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

}