package com.example.grocerygo.activities_and_frags

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterProducts
import com.example.grocerygo.adapters.AdapterProfile
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.easySnackbar
import com.example.grocerygo.inheritables.ActivityHostCallbacks
import com.example.grocerygo.inheritables.GGActivityCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.models.ProfileItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.frag_profile.*
import kotlinx.android.synthetic.main.frag_search_lower.*

class FragProfile : TMFragment() {
    val title = "Profile"
    override val layout: Int
        get() = R.layout.frag_profile

    override fun onStart() {
        super.onStart()
        (activity as GGActivityCallbacks).setToolbarAttributes(title, true)
        setupListeners()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val profileItems = ArrayList<ProfileItem>()
        profileItems.add(ProfileItem("Name", App.sm.user.name?:""))
        profileItems.add(ProfileItem("Email", App.sm.user.email?:""))
        profileItems.add(ProfileItem("Password", "*****"))
        profileItems.add(ProfileItem("Mobile", App.sm.user.mobile?:""))
        recycler_view_profile.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_profile.adapter = AdapterProfile(activity!!, profileItems)
        recycler_view_profile
            .addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
    }

    private fun setupListeners() {
//        button_save.setOnClickListener {
//            App.sm.registerName(edit_text_user_name.text.toString())
//            App.sm.registerEmail(edit_text_user_email.text.toString())
//        }
        button_logout.setOnClickListener {
            App.sm.logout()
            (activity as ActivityHostCallbacks).goToProfile()
        }
//        text_input_name.setOnClickListener {
//
//            Snackbar
//                .make(coordinator_layout_profile, "Would you like to edit your profile?", Snackbar.LENGTH_LONG)
//                .setAction("YES",View.OnClickListener { fab.visibility=View.VISIBLE })
//                .setBackgroundTint(Color.GRAY)
//                .setTextColor(Color.BLACK)
//                .show()
//        }
        fab.setOnClickListener {
            easySnackbar(coordinator_layout_profile, "Saved")
        }
    }
}
