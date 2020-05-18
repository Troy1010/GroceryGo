package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.easyToast
import com.example.grocerygo.inheritables.ActivityHostCallbacks
import com.example.grocerygo.inheritables.GGFragment
import com.example.grocerygo.models.LoginObject
import kotlinx.android.synthetic.main.frag_login.*


class FragProfileLogin : GGFragment() {
    override val title: String
        get() = "Login"
    override val layout: Int
        get() = R.layout.frag_login

    override fun onStart() {
        super.onStart()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        button_not_yet_registered.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frame_fragments, FragProfileRegister()).commit()
        }
        button_login_send.setOnClickListener {
            val user =
                LoginObject(edit_text_email.text.toString(), edit_text_password.text.toString())
            if (!App.sm.attemptLogin(user)) {
                this.easyToast("LOGIN FAILED")
            } else {
                (activity as ActivityHostCallbacks).goToHome()
            }
        }
    }
}