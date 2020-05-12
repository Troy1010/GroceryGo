package com.example.grocerygo.activities_and_frags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*

class ActivityProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
    }

    private fun init() {
        initializeUserValues()
        setupListeners()
    }

    private fun initializeUserValues() {
        edit_text_user_name.setText(App.sm.user.name)
        edit_text_user_email.setText(App.sm.user.email)
    }

    private fun setupListeners() {
        edit_text_user_name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                App.sm.registerName(edit_text_user_name.text.toString())
            }
        }
        edit_text_user_email.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                Log.d("TMLog","`emailFocusChange`edit_text_user_email.text.toString():${edit_text_user_email.text}")
                App.sm.registerEmail(edit_text_user_email.text.toString())
            }
        }
    }
}
