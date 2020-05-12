package com.example.grocerygo.activities_and_frags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.models.LoginObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        setupClickListeners()
        setupToolbar("Login")
    }

    private fun setupClickListeners() {
        button_not_yet_registered.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }
        button_login_send.setOnClickListener {
            val user = LoginObject(edit_text_email.text.toString(), edit_text_password.text.toString())
            if (App.sm.attemptLogin(user)){
                startActivity(Intent(this, ActivityHome::class.java))
            } else {
                Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
            }
        }
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
}
