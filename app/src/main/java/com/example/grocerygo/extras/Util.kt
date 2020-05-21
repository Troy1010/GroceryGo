package com.example.grocerygo.extras

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.grocerygo.models.Product
import com.google.android.material.snackbar.Snackbar

fun logz (msg:String) {
    Log.d("TMLog", "TM`$msg")
}


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