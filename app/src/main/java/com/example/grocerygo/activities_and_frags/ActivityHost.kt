package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.View
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.HostCallbacks
import com.example.grocerygo.extras.App
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.activity_host.*

class ActivityHost : GGToolbarActivity(layout = R.layout.activity_host),
    HostCallbacks {
    override val title: String
        get() = "Host"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigationBar()
    }

    override fun onStart() {
        super.onStart()
        goToHome()
    }

    override fun showNavigationBar(showNavigationBar: Boolean) {
        if (showNavigationBar) {
            bottom_navigation_bar.visibility=View.VISIBLE
        } else {
            bottom_navigation_bar.visibility=View.GONE
        }
    }

    private fun setupNavigationBar() {
        bottom_navigation_bar.setOnNavigationItemSelectedListener { item ->
            val frag = when (item.itemId) {
                R.id.item_profile -> if (App.sm.isLoggedIn()) FragProfile() else FragProfileLogin()
                R.id.item_search -> FragSearchCategories()
                R.id.item_cart -> FragCart()
                else -> FragHome()
            }
            supportFragmentManager.beginTransaction().replace(R.id.frame_fragments, frag).commit()
            // TODO add to backstack and fix highlighting
            true
        }
    }

    override fun goToHome() {
        bottom_navigation_bar.selectedItemId = R.id.item_home
    }

    override fun goToProfile() {
        bottom_navigation_bar.selectedItemId = R.id.item_profile
    }

}
