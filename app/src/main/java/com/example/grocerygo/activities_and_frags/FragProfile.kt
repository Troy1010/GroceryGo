package com.example.grocerygo.activities_and_frags

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.Title
import com.example.grocerygo.extras.easySnackbar
import com.example.grocerygo.extras.easyToast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.frag_profile.*

class FragProfile : Fragment(), Title {
    override val title = "Profile"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_profile, container,false )
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        initializeUserValues()
        setupListeners()
    }

    private fun initializeUserValues() {
//        edit_text_user_name.setText(App.sm.user.name) TODO
//        edit_text_user_email.setText(App.sm.user.email)
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
