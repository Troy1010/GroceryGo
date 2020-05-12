package com.example.grocerygo.activities_and_frags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
    }

    private fun init() {
        initializeUserValues()
        setupListeners()
        setupToolbar("Profile")
    }

    private fun setupToolbar(title:String) { // TODO refactor this out
        toolbar_top.title = title
        setSupportActionBar(toolbar_top)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // TODO refactor
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // TODO refactor
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

    private fun initializeUserValues() {
        edit_text_user_name.setText(App.sm.user.name)
        edit_text_user_email.setText(App.sm.user.email)
    }

    private fun setupListeners() {
        button_save.setOnClickListener {
            App.sm.registerName(edit_text_user_name.text.toString())
            App.sm.registerEmail(edit_text_user_email.text.toString())
        }
    }
}
