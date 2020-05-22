package com.example.grocerygo.activities_and_frags

import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.easyToast
import com.example.grocerygo.inheritables.HostCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.inheritables.ToolbarCallbacks
import com.example.grocerygo.models.LoginObject
import kotlinx.android.synthetic.main.frag_login.*


class FragProfileLogin : TMFragment() {
    override val layout: Int
        get() = R.layout.frag_login

    override fun onStart() {
        super.onStart()
        setupParent()
        setupClickListeners()
    }

    private fun setupParent() {
        (activity as HostCallbacks).showNavigationBar(true)
        (activity as ToolbarCallbacks).showCart(true)
        (activity as ToolbarCallbacks).showBack(true)
        (activity as ToolbarCallbacks).setTitle("Login")
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
                (activity as HostCallbacks).goToHome()
            }
        }
    }
}