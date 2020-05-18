package com.example.grocerygo.activities_and_frags

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
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
        setupNavigationBar()
    }

    override fun onStart() {
        super.onStart()
        bottom_navigation_bar.selectedItemId = R.id.item_home
    }

    private fun setupNavigationBar() {
        bottom_navigation_bar.setOnNavigationItemSelectedListener { item ->
            val frag = when (item.itemId) {
                R.id.item_profile -> FragProfile()
                R.id.item_search -> FragSearchPrimary()
                R.id.item_cart -> FragCart()
                else -> FragHome()
            }
            supportFragmentManager.beginTransaction().replace(R.id.frame_fragments, frag).commit()
            true
        }
    }

}
