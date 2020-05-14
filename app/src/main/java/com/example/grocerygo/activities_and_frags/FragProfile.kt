package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.extras.AppCompatActivityWithToolbarFunctionality
import com.example.grocerygo.extras.setup
import kotlinx.android.synthetic.main.frag_profile.*
import kotlinx.android.synthetic.main.app_toolbar.*

class FragProfile : Fragment() {
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
        edit_text_user_name.setText(App.sm.user.name)
        edit_text_user_email.setText(App.sm.user.email)
    }

    private fun setupListeners() {
//        button_save.setOnClickListener {
//            App.sm.registerName(edit_text_user_name.text.toString())
//            App.sm.registerEmail(edit_text_user_email.text.toString())
//        }
    }
}
