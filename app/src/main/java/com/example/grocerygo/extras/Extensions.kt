package com.example.grocerygo.extras

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


fun Toolbar.setup(activity: AppCompatActivity, title:String) {
    this.title = title
    activity.setSupportActionBar(this)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
}
fun Context.easyToast (s:String) {
    Toast.makeText(this,s, Toast.LENGTH_SHORT).show()
}

fun String.hasDigit () : Boolean {
    for (ch in this) {
        if (ch.isDigit()) {
            return true
        }
    }
    return false
}

fun String.isAllDigits () : Boolean {
    for (ch in this) {
        if (!ch.isDigit()) {
            return false
        }
    }
    return true
}