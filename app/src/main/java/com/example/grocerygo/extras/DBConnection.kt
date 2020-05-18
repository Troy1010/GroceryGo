package com.example.grocerygo.extras

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.grocerygo.models.Product

class DBConnection :
    SQLiteOpenHelper(App.instance, DATABASE_NAME, null, DATABASE_VERSION) {
    private val databaseSQL = writableDatabase!!

    companion object {
        const val DATABASE_NAME = "GroceryGoDB"
        const val DATABASE_VERSION = 5
        const val TABLE_NAME = "Products"
        const val COL_PRICE = "Price"
        const val COL_NAME = "Name"
        const val COL_ID = "ID"
        const val COL_QUANTITY = "Quantity"
        const val COL_BACKEND_ID = "BackendID" //maybe they don't need char 100..?
    }

    override fun onCreate(sqlDatabase: SQLiteDatabase) {
        logz("DBConnection`onCreate")
        val sqlCreateTable =
            """create table $TABLE_NAME($COL_NAME char(50), 
                   $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                   $COL_PRICE MONEY,
                   $COL_QUANTITY INT,
                   $COL_BACKEND_ID char(250) 
                   )"""
        sqlDatabase.execSQL(sqlCreateTable)
    }
    fun getProductByBackendID(_id:String) : Product? {
        val cursor = databaseSQL.rawQuery("Select * from $TABLE_NAME where $COL_BACKEND_ID=?",arrayOf(_id))
        if (cursor != null && cursor.moveToFirst()) {
            return generateProductFromPrimedCursor(cursor)
        } else {
            logz("getProductByBackendID`Could not find product:${_id}")
            return null
        }
    }
    fun getProductQuantityByBackendID(_id:String) :Int? {
        val cursor = databaseSQL.rawQuery("Select * from $TABLE_NAME where $COL_BACKEND_ID=?",arrayOf(_id))
        if (cursor != null && cursor.moveToFirst()) {
            val returning = cursor.getInt(cursor.getColumnIndex(COL_QUANTITY))
            return returning
        } else {
            logz("getProductByBackendID`Could not find product:${_id}")
            return null
        }
    }

    fun addProduct(product: Product) {
        if (hasProduct(product)) {
            val oldProduct = getProductByBackendID(product._id)!!
            product.quantity = (getProductQuantityByBackendID(product._id)?:0) + 1
            product.sqlID = oldProduct.sqlID
            updateProduct(product)
        } else {
            product.quantity = 1
            val contentValues = ContentValues()
            contentValues.put(COL_NAME, product.productName)
            contentValues.put(COL_PRICE, product.price)
            contentValues.put(COL_QUANTITY, product.quantity)
            contentValues.put(COL_BACKEND_ID, product._id)
            databaseSQL.insert(TABLE_NAME, null, contentValues)
        }
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
        contentValues.put(COL_QUANTITY, product.quantity)
        databaseSQL.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun getProducts(): ArrayList<Product> {
        val returningList = ArrayList<Product>()
        val columns = arrayOf(COL_ID, COL_NAME, COL_PRICE, COL_QUANTITY, COL_BACKEND_ID)
        val cursor = databaseSQL.query(TABLE_NAME, columns, null, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                returningList.add(generateProductFromPrimedCursor(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return returningList
    }

    private fun generateProductFromPrimedCursor(cursor: Cursor):Product {
        return Product(
            productName = cursor.getString(cursor.getColumnIndex(COL_NAME)),
            sqlID = cursor.getInt(cursor.getColumnIndex(COL_ID)),
            price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE)),
            quantity = cursor.getInt(cursor.getColumnIndex(COL_QUANTITY)),
            _id = cursor.getString(cursor.getColumnIndex(COL_BACKEND_ID))
        )
    }

    fun hasProduct(product:Product) :Boolean {
        val cursor = databaseSQL.rawQuery("Select * from $TABLE_NAME where $COL_BACKEND_ID=?",arrayOf(product._id))
        return cursor.count != 0 // TODO close cursor?
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
