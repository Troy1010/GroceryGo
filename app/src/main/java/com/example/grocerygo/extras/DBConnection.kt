package com.example.grocerygo.extras

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.grocerygo.models.Product

class DBConnection :
    SQLiteOpenHelper(App.instance, DATABASE_NAME, null, DATABASE_VERSION) {
    private val databaseSQL = writableDatabase!!

    companion object {
        const val DATABASE_NAME = "GroceryGoDB"
        const val DATABASE_VERSION = 3
        const val TABLE_NAME = "Products"
        const val COL_PRICE = "Price"
        const val COL_NAME = "Name"
        const val COL_ID = "ID"
    }

    override fun onCreate(sqlDatabase: SQLiteDatabase) {
        logz("DBConnection`onCreate")
        val sqlCreateTable =
            """create table $TABLE_NAME($COL_NAME char(50), 
                   $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                   $COL_PRICE MONEY
                   )"""
        sqlDatabase.execSQL(sqlCreateTable)
    }

    fun add(product: Product) {
        logz("DBConnection`addProduct. $product")
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, product.productName)
        contentValues.put(COL_PRICE, product.price)
        databaseSQL.insert(TABLE_NAME, null, contentValues)
    }

    fun updateProduct(product: Product) {
        if (product.sqlID == 0) {
            logz("DBConnection`Tried to use updateProduct on a product without a sqlID")
            return
        }
        val whereClause = "$COL_ID=?"
        val whereArgs = arrayOf(product.sqlID.toString())
        var contentValues = ContentValues()
        contentValues.put(COL_NAME, product.productName)
        contentValues.put(COL_PRICE, product.price)
        databaseSQL.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun getProducts(): ArrayList<Product> {
        val returningList = ArrayList<Product>()
        val columns = arrayOf(COL_ID, COL_NAME, COL_PRICE)
        val cursor = databaseSQL.query(TABLE_NAME, columns, null, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                returningList.add(
                    Product(
                        productName = cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        sqlID = cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return returningList
    }

    ////////////////

    override fun onUpgrade(sqlDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        logz("DBConnection`onUpgrade")
        val sqlDropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        sqlDatabase.execSQL(sqlDropTable)
        this.onCreate(sqlDatabase)
    }

    fun deleteProduct(product: Product) {
        val whereClause = "$COL_ID=?"
        val whereArgs = arrayOf(product.sqlID.toString())
        databaseSQL.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun deleteProductByIndex(i: Int) {
        val products = getProducts()
        if (!products.hasKey(i)) {
            logz("DBConnection`Tried to delete from a non-existent index")
        } else {
            deleteProduct(products[i])
        }
    }

}