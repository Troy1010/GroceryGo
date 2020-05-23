package com.example.grocerygo.activities_and_frags.Inheritables

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class TMActivity: AppCompatActivity() {
    abstract val layout:Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }
}