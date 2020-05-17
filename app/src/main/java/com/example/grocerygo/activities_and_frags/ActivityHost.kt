package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.MenuItem
import com.example.grocerygo.R
import com.example.grocerygo.extras.PageEnums
import com.example.grocerygo.extras.setup
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityHost : GGToolbarActivity() {
    override val title: String
        get() = "Host"
    override val layout: Int
        get() = R.layout.activity_host

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToPage(PageEnums.HOME)
        setupNavigationBar()
    }

    private fun setupNavigationBar() {
        bottom_navigation_bar.setOnNavigationItemSelectedListener { item ->
            val e = when (item.itemId) {
                R.id.item_home -> PageEnums.HOME
                R.id.item_profile -> PageEnums.PROFILE
                R.id.item_search -> PageEnums.SEARCH
                R.id.item_cart -> PageEnums.CART
                else -> null
            }
            if (e==null) {
                false
            } else {
                navigateToPage(e)
                true
            }
        }
    }

}
