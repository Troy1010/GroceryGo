package com.example.grocerygo.extras

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.grocerygo.models.Product
import com.google.android.material.snackbar.Snackbar




fun easySnackbar(
    coordinatorLayout: CoordinatorLayout,
    msg: String,
    actionText: String? = null,
    action: View.OnClickListener? = null
) {
    var sb = Snackbar
        .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
        .setBackgroundTint(Color.GRAY)
        .setTextColor(Color.BLACK)

    if ((actionText != null) and (action != null)) {
        sb.setAction(actionText, action)
    }
    sb.show()
}

fun max (num1: Int, num2: Int):Int {
    return if (num1 > num2) {
        num1
    } else {
        num2
    }
}

fun round(value:Double):Int {
    // TODO test if this works..
    if (value % 1 > 0.5) {
        return value.toInt() + 1
    } else {
        return value.toInt()
    }
}