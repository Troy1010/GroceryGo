package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.View
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.inheritables.HostCallbacks
import com.example.grocerygo.inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.activity_host.*

class ActivityHost : GGToolbarActivity(), HostCallbacks {
    override val title: String
        get() = "Host"
    override val layout: Int
        get() = R.layout.activity_host

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

    override fun setNavigationEmpty(shouldNavigationBarBeEmpty: Boolean) {
        if (shouldNavigationBarBeEmpty) {
            bottom_navigation_bar.visibility=View.GONE
        } else {
            bottom_navigation_bar.visibility=View.VISIBLE
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
