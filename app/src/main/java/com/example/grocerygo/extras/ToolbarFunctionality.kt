package com.example.grocerygo.extras

import android.app.Activity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatCallback
import com.example.grocerygo.R

abstract class AppCompatActivityWithToolbarFunctionality : AppCompatActivity(), AppCompatCallback {


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home->{
                finish()
            }
            R.id.menu_cart -> {
                Log.d("TMLog","OptionsMenu`Selected Cart")
            }
            R.id.menu_profile -> {
                Log.d("TMLog","OptionsMenu`Selected Profile")
            }
            R.id.menu_settings -> {
                Log.d("TMLog","OptionsMenu`Selected Settings")
            }
        }
        return true
    }
}