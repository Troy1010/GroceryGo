package com.example.grocerygo.activities_and_frags.Inheritables

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.getView
import com.example.grocerygo.extras.logz

abstract class TMActivity(open val layout:Int): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(App.sm.theme)
        setContentView(layout)
        //setBackgroundColor to my theme's colorBackground attribute
        val ta = obtainStyledAttributes(App.sm.theme, intArrayOf(R.attr.colorBackground))
        val colorBackground = ta.getInt(0, 0)
        this.getView()?.setBackgroundColor(colorBackground)
        ta.recycle()
    }
}