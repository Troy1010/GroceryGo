package com.example.grocerygo.inheritables

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class TMActivity: AppCompatActivity() {
    abstract val layout:Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }
}