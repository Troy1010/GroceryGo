package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.extras.AppCompatActivityWithToolbarFunctionality
import com.example.grocerygo.extras.setup
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityWithProfile : AppCompatActivityWithToolbarFunctionality() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
    }

    private fun init() {
        initializeUserValues()
        setupListeners()
        toolbar_top.setup(this, "Profile")
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
