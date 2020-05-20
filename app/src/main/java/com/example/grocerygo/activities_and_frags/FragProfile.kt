package com.example.grocerygo.activities_and_frags

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.easySnackbar
import com.example.grocerygo.inheritables.ActivityHostCallbacks
import com.example.grocerygo.inheritables.GGToolbarActivityCallbacks
import com.example.grocerygo.inheritables.RecyclerViewActivityCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.models.ProfileItem
import kotlinx.android.synthetic.main.frag_profile.*
import kotlinx.android.synthetic.main.item_profile.view.*

class FragProfile : TMFragment(), RecyclerViewActivityCallbacks {
    val title = "Profile"
    override val layout: Int
        get() = R.layout.frag_profile
    val profileItems = arrayListOf(
        ProfileItem("Name", App.sm.user.name ?: ""),
        ProfileItem("Email", App.sm.user.email ?: ""),
        ProfileItem("Password", "*****"),
        ProfileItem("Mobile", App.sm.user.mobile ?: "")
    )

    override fun onStart() {
        super.onStart()
        (activity as GGToolbarActivityCallbacks).setToolbarAttributes(title, true)
        (activity as ActivityHostCallbacks).setNavigationEmpty(false)
        setupListeners()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view_profile.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_profile.adapter = AdapterRecyclerView(this, activity!!, R.layout.item_profile)
        recycler_view_profile
            .addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
    }

    private fun setupListeners() {
        button_logout.setOnClickListener {
            App.sm.logout()
            (activity as ActivityHostCallbacks).goToProfile()
        }
        fab.setOnClickListener {
            easySnackbar(coordinator_layout_profile, "Saved")
        }
    }

    override fun bindRecyclerItemView(view: View, i: Int) {
        view.text_view_title.text = profileItems[i].title
        view.text_view_value.text = profileItems[i].value
    }

    override fun getRecyclerDataSize(): Int {
        return profileItems.size
    }
}
