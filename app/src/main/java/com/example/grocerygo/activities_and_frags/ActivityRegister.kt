package com.example.grocerygo.activities_and_frags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.extras.setup
import com.example.grocerygo.models.User
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        button_register_send.setOnClickListener {
            var name = edit_text_name.text.toString().trim()
            var email = edit_text_email.text.toString().trim()
            var password = edit_text_password.text.toString().trim()
            App.sm.register(User(name, email, password))
            startActivity(Intent(this, ActivityHome::class.java))
        }
        toolbar_top.setup(this, "Register")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // TODO refactor
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // TODO refactor
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.menu_cart -> {
                Log.d("TMLog", "OptionsMenu`Selected Cart")
            }
            R.id.menu_profile -> {
                Log.d("TMLog", "OptionsMenu`Selected Profile")
            }
            R.id.menu_settings -> {
                Log.d("TMLog", "OptionsMenu`Selected Settings")
            }
        }
        return true
    }
}
