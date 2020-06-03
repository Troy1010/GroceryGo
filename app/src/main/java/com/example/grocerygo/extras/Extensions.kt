package com.example.grocerygo.extras

import android.content.Context
import android.service.autofill.Validators.and
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.example.grocerygo.models.Product
import com.example.tmcommonkotlin.logz
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.frag_profile.*

fun String.noDoubleSpaces():String {
    return if (this.contains("  ")) {
        this.replace("  ", " ").noDoubleSpaces()
    } else {
        this
    }
}

fun AppCompatActivity.getView(): View? {
    val returning = this.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
    if (returning==null) {
        logz("AppCompatActivity.getView`returning null")
    }
    return returning
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round((this * multiplier)) / multiplier
}

fun Context.easyToast(s: String) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}

fun Fragment.easyToast(s: String) {
    this.activity?.easyToast(s)
}

fun String.hasDigit(): Boolean {
    for (ch in this) {
        if (ch.isDigit()) {
            return true
        }
    }
    return false
}

fun String.isAllDigits(): Boolean {
    for (ch in this) {
        if (!ch.isDigit()) {
            return false
        }
    }
    return true
}


fun <E> ArrayList<E>.narrate(): String {
    val stingList = ArrayList<String>()
    stingList.add("Collection(${this.size})..")
    for (x in this) {
        stingList.add("\t${x.toString()}")
    }
    return stingList.joinToString(separator = "\n")
}


fun ArrayList<Product>.hasKey(i: Int): Boolean {
    return i < this.size
}



fun ImageView.easyPicasso(endpoint:String) {
    com.squareup.picasso.Picasso.get()
        .load(endpoint)
        .placeholder(com.example.grocerygo.R.drawable.not_found)
        .error(com.example.grocerygo.R.drawable.no_image_available_vector_illustration_260nw_744886198)
        .into(this)
}