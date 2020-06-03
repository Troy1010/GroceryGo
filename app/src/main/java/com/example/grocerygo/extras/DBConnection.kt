package com.example.grocerygo.extras

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.grocerygo.models.OrderSummary
import com.example.grocerygo.models.Product
import com.example.tmcommonkotlin.logz

class DBConnection :
    SQLiteOpenHelper(App.instance, DATABASE_NAME, null, DATABASE_VERSION) {
    private val databaseSQL = writableDatabase!!

    companion object {
        const val DATABASE_NAME = "GroceryGoDB"
        const val DATABASE_VERSION = 8
        const val TABLE_NAME = "Products"
        const val COL_PRICE = "Price"
        const val COL_FAKE_PRICE = "FakePrice"
        const val COL_NAME = "Name"
        const val COL_QUANTITY = "Quantity"
        const val COL_PRODUCT_ID = "ProductID"
        const val COL_IMAGE_NAME = "ImageName"
    }

    override fun onCreate(sqlDatabase: SQLiteDatabase) {
        logz("DBConnection`onCreate")
        val sqlCreateTable =
            """create table $TABLE_NAME($COL_NAME char(50), 
                   $COL_PRICE MONEY,
                   $COL_QUANTITY INT,
                   $COL_PRODUCT_ID char(250),
                   $COL_IMAGE_NAME char(250),
                   $COL_FAKE_PRICE MONEY
                   )"""
        sqlDatabase.execSQL(sqlCreateTable)
    }

    fun getProducts(): ArrayList<Product> {
        val returningList = ArrayList<Product>()
        val columns = arrayOf(COL_NAME, COL_PRICE, COL_QUANTITY, COL_PRODUCT_ID, COL_IMAGE_NAME, COL_FAKE_PRICE)
        val cursor = databaseSQL.query(TABLE_NAME, columns, null, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                returningList.add(generateProductFromPrimedCursor(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return returningList
    }

    private fun generateContentValuesFromProduct(product: Product) : ContentValues {
        val returning = ContentValues()
        returning.put(COL_NAME, product.productName)
        returning.put(COL_PRICE, product.price)
        returning.put(COL_QUANTITY, product.quantity)
        returning.put(COL_PRODUCT_ID, product._id)
        returning.put(COL_IMAGE_NAME, product.image)
        returning.put(COL_FAKE_PRICE, product.mrp)
        return returning
    }

    private fun generateProductFromPrimedCursor(cursor: Cursor): Product {
        return Product(
            productName = cursor.getString(cursor.getColumnIndex(COL_NAME)),
            price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE)),
            quantity = cursor.getInt(cursor.getColumnIndex(COL_QUANTITY)),
            _id = cursor.getString(cursor.getColumnIndex(COL_PRODUCT_ID)),
            image = cursor.getString(cursor.getColumnIndex(COL_IMAGE_NAME)),
            mrp = cursor.getDouble(cursor.getColumnIndex(COL_FAKE_PRICE))
        )
    }

    //////////////// DOES NOT DEAL WITH ALL COLUMNS

    fun addProduct(product: Product) {
        if (hasProduct(product)) {
            product.quantity = (getProductQuantityByProductID(product._id) ?: 0) + 1
            updateProduct(product)
        } else {
            product.quantity = 1
            val contentValues = generateContentValuesFromProduct(product)
            databaseSQL.insert(TABLE_NAME, null, contentValues)
        }
    }

    fun updateProduct(product: Product) {
        if (product._id == "") {
            logz("DBConnection`Tried to use updateProduct on a product without a ProductID")
            return
        }
        val whereArgs = arrayOf(product._id)
        val whereClause = "$COL_PRODUCT_ID=?"
        var contentValues = generateContentValuesFromProduct(product)
        databaseSQL.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun minusProduct(product: Product) {
        if (hasProduct(product)) {
            product.quantity = maxOf(0, (getProductQuantityByProductID(product._id) ?: 0) - 1)
            if (product.quantity == 0) {
                deleteProduct(product)
            } else {
                updateProduct(product)
            }
        } else {
            logz("DBConnection`minusProduct`attempted to subtract quantity from a product which is not in DB.")
        }
    }

    fun getProductByProductID(_id: String): Product? {
        val cursor =
            databaseSQL.rawQuery("Select * from $TABLE_NAME where $COL_PRODUCT_ID=?", arrayOf(_id))
        if (cursor != null && cursor.moveToFirst()) {
            val product = generateProductFromPrimedCursor(cursor)
            cursor.close()
            return product
        } else {
            return null
        }
    }

    fun getProductQuantityByProductID(_id: String): Int? {
        val cursor =
            databaseSQL.rawQuery("Select * from $TABLE_NAME where $COL_PRODUCT_ID=?", arrayOf(_id))
        if (cursor != null && cursor.moveToFirst()) {
            val quantity = cursor.getInt(cursor.getColumnIndex(COL_QUANTITY))
            cursor.close()
            return quantity
        } else {
            return null
        }
    }

    fun hasProduct(product: Product): Boolean {
        val cursor = databaseSQL.rawQuery(
            "Select * from $TABLE_NAME where $COL_PRODUCT_ID=?",
            arrayOf(product._id)
        )
        val hasProduct = cursor.count != 0
        cursor.close()
        return hasProduct
    }

    fun clear() {
        val sqlDropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        databaseSQL.execSQL(sqlDropTable)
        this.onCreate(databaseSQL)
    }

    override fun onUpgrade(sqlDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        logz("DBConnection`onUpgrade")
        this.clear()
    }

    fun deleteProduct(product: Product) {
        val whereClause = "$COL_PRODUCT_ID=?"
        val whereArgs = arrayOf(product._id.toString())
        databaseSQL.delete(TABLE_NAME, whereClause, whereArgs)
    }

}
