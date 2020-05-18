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
        const val DATABASE_VERSION = 6
        const val TABLE_NAME = "Products"
        const val COL_PRICE = "Price"
        const val COL_NAME = "Name"
        const val COL_QUANTITY = "Quantity"
        const val COL_PRODUCT_ID = "ProductID"
    }

    override fun onCreate(sqlDatabase: SQLiteDatabase) {
        logz("DBConnection`onCreate")
        val sqlCreateTable =
            """create table $TABLE_NAME($COL_NAME char(50), 
                   $COL_PRICE MONEY,
                   $COL_QUANTITY INT,
                   $COL_PRODUCT_ID char(250) 
                   )"""
        sqlDatabase.execSQL(sqlCreateTable)
    }
    fun getProductByProductID(_id:String) : Product? {
        val cursor = databaseSQL.rawQuery("Select * from $TABLE_NAME where $COL_PRODUCT_ID=?",arrayOf(_id))
        if (cursor != null && cursor.moveToFirst()) {
            return generateProductFromPrimedCursor(cursor)
        } else {
            return null
        }
    }
    fun getProductQuantityByProductID(_id:String) :Int? {
        val cursor = databaseSQL.rawQuery("Select * from $TABLE_NAME where $COL_PRODUCT_ID=?",arrayOf(_id))
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(COL_QUANTITY))
        } else {
            return null
        }
    }

    fun addProduct(product: Product) {
        if (hasProduct(product)) {
            product.quantity = (getProductQuantityByProductID(product._id)?:0) + 1
            updateProduct(product)
        } else {
            product.quantity = 1
            val contentValues = ContentValues()
            contentValues.put(COL_NAME, product.productName)
            contentValues.put(COL_PRICE, product.price)
            contentValues.put(COL_QUANTITY, product.quantity)
            contentValues.put(COL_PRODUCT_ID, product._id)
            databaseSQL.insert(TABLE_NAME, null, contentValues)
        }
    }

    fun minusProduct(product: Product) {
        if (hasProduct(product)) {
            product.quantity = maxOf(0,(getProductQuantityByProductID(product._id)?:0) - 1)
            updateProduct(product)
        } else {
            logz("DBConnection`minusProduct`attempted to subtract quantity from a product which is not in DB.")
        }
    }

    fun updateProduct(product: Product) {
        if (product._id=="") {
            logz("DBConnection`Tried to use updateProduct on a product without a ProductID")
            return
        }
        val whereArgs = arrayOf(product._id)
        val whereClause = "$COL_PRODUCT_ID=?"
        var contentValues = ContentValues()
        contentValues.put(COL_NAME, product.productName)
        contentValues.put(COL_PRICE, product.price)
        contentValues.put(COL_QUANTITY, product.quantity)
        databaseSQL.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun getProducts(): ArrayList<Product> {
        val returningList = ArrayList<Product>()
        val columns = arrayOf(COL_NAME, COL_PRICE, COL_QUANTITY, COL_PRODUCT_ID)
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
            price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE)),
            quantity = cursor.getInt(cursor.getColumnIndex(COL_QUANTITY)),
            _id = cursor.getString(cursor.getColumnIndex(COL_PRODUCT_ID))
        )
    }

    ////////////////

    fun hasProduct(product:Product) :Boolean {
        val cursor = databaseSQL.rawQuery("Select * from $TABLE_NAME where $COL_PRODUCT_ID=?",arrayOf(product._id))
        return cursor.count != 0 // TODO close cursor?
    }

    override fun onUpgrade(sqlDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        logz("DBConnection`onUpgrade")
        val sqlDropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        sqlDatabase.execSQL(sqlDropTable)
        this.onCreate(sqlDatabase)
    }

    fun deleteProduct(product: Product) {
        val whereClause = "$COL_PRODUCT_ID=?"
        val whereArgs = arrayOf(product._id.toString())
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
