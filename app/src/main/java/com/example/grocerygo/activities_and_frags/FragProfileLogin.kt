package com.example.grocerygo.activities_and_frags

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.extras.logz
import com.example.grocerygo.inheritables.HostCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.inheritables.ToolbarCallbacks
import com.example.grocerygo.models.ReceivedLoginObject
import com.example.grocerygo.models.User
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_login.*
import org.json.JSONObject


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
            requestLogin(User(name = null, email = edit_text_email.text.toString(), password = edit_text_password.text.toString(), mobile = null))
        }
    }


    private fun requestLogin(user: User) {
        val requestQueue = Volley.newRequestQueue(activity!!)
        val params = HashMap<String, String>()
        params["email"] = user.email!!
        params["password"] = user.password!!
        //typecast params into jsonObject
        val jsonObject = JSONObject(params as Map<*, *>)

        val request = JsonObjectRequest(
            Request.Method.POST, Endpoints.login, jsonObject,
            Response.Listener { response ->
                val receivedCategoriesObject = GsonBuilder().create()
                    .fromJson(response.toString(), ReceivedLoginObject::class.java)
                //
                val registrationData = receivedCategoriesObject.user
                App.sm.user = User(
                    name = registrationData.firstName,
                    email = registrationData.email,
                    password = registrationData.password,
                    mobile = registrationData.mobile,
                    id = registrationData._id
                )
                logz("App.sm.user:${App.sm.user}")
                //
                (activity as HostCallbacks).goToHome()
            },
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }
}