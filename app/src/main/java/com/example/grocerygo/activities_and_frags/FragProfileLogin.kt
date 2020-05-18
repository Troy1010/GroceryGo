package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.easyToast
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.models.LoginObject
import kotlinx.android.synthetic.main.activity_login.*


class FragProfileLogin : TMFragment() {
    val title = "Login"
    override val layout: Int
        get() = R.layout.frag_login



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        button_not_yet_registered.setOnClickListener {
            startActivity(Intent(activity!!, ActivityRegister::class.java))
        }
        button_login_send.setOnClickListener {
            val user = LoginObject(edit_text_email.text.toString(), edit_text_password.text.toString())
            if (App.sm.attemptLogin(user)){

                startActivity(Intent(activity!!, ActivityHost::class.java))
            } else {
                this.easyToast("LOGIN FAILED")
            }
        }
    }
}