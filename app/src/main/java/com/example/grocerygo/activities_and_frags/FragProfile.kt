package com.example.grocerygo.activities_and_frags

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.easySnackbar
import com.example.grocerygo.inheritables.GGActivityCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.frag_profile.*

class FragProfile : TMFragment() {
    val title = "Profile"
    override val layout: Int
        get() = R.layout.frag_profile

    override fun onStart() {
        super.onStart()
        (activity as GGActivityCallbacks).setToolbarAttributes(title, true)
        initializeUserValues()
        setupListeners()
    }

    private fun initializeUserValues() {
        text_input_name.setText(App.sm.user.name)
        text_input_email.setText(App.sm.user.email)
        text_input_password.setText("*".repeat(App.sm.user.password?.length?:0))
        text_input_mobile.setText(App.sm.user.mobile)
    }

    private fun setupListeners() {
//        button_save.setOnClickListener {
//            App.sm.registerName(edit_text_user_name.text.toString())
//            App.sm.registerEmail(edit_text_user_email.text.toString())
//        }
        text_input_name.setOnClickListener {

            Snackbar
                .make(coordinator_layout_profile, "Would you like to edit your profile?", Snackbar.LENGTH_LONG)
                .setAction("YES",View.OnClickListener { fab.visibility=View.VISIBLE })
                .setBackgroundTint(Color.GRAY)
                .setTextColor(Color.BLACK)
                .show()
        }
        fab.setOnClickListener {
            easySnackbar(coordinator_layout_profile,"Saved")
        }
    }
}
