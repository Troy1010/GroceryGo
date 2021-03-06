package com.example.grocerygo.activities_and_frags.Inheritables

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.getView

abstract class TMActivity(open val layout:Int): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(App.sm.theme)
        setContentView(layout)
    }
}