package com.example.grocerygo.activities_and_frags

import com.android.volley.Response
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.HostCallbacks
import com.example.grocerygo.activities_and_frags.Inheritables.ToolbarCallbacks
import com.example.grocerygo.extras.*
import com.example.grocerygo.extras.App
import com.example.grocerygo.activities_and_frags.Inheritables.TMFragment
import com.example.grocerygo.models.received.ReceivedLoginObject
import com.example.grocerygo.models.User
import com.google.gson.GsonBuilder
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
            val user = User(name = null, email = edit_text_email.text.toString(), password = edit_text_password.text.toString(), mobile = null)
            Requester.requestLogin(user,
                Response.Listener { response ->
                    val receivedLoginObject = GsonBuilder().create()
                        .fromJson(response.toString(), ReceivedLoginObject::class.java)
                    //
                    val registrationData = receivedLoginObject.user
                    App.sm.user = User(
                        name = registrationData.firstName,
                        email = registrationData.email,
                        password = registrationData.password,
                        mobile = registrationData.mobile,
                        _id = registrationData._id
                    )
                    //
                    (activity as HostCallbacks).goToHome()
                })
        }
    }
}