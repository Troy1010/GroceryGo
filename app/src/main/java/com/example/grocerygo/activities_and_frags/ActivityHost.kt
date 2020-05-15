package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.grocerygo.R
import com.example.grocerygo.extras.AppCompatActivityWithToolbarFunctionality
import com.example.grocerygo.extras.PageEnums
import com.example.grocerygo.extras.Title
import com.example.grocerygo.extras.setup
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityHost : AppCompatActivityWithToolbarFunctionality(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        init()
    }

    private fun init() {
        toolbar_top.setup(this, "Home")
        supportFragmentManager.beginTransaction().add(R.id.frame_page_fragments,FragHome()).commit()
        bottom_navigation_bar.setOnNavigationItemSelectedListener(this)
    }

    fun navigateToPage(e:PageEnums) {
        val frag:Fragment = when (e) {
            PageEnums.SEARCH -> FragSearch()
            PageEnums.PROFILE -> FragProfile()
            PageEnums.CART -> FragCart()
            else -> FragHome()
        }
        if (frag is Title) {
            toolbar_top.title = frag.title
        }
        supportFragmentManager.beginTransaction().replace(R.id.frame_page_fragments,frag).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_home -> {
                navigateToPage(PageEnums.HOME)
                true
            }
            R.id.item_profile -> {
                navigateToPage(PageEnums.PROFILE)
                true
            }
            R.id.item_search -> {
                navigateToPage(PageEnums.SEARCH)
                true
            }
            R.id.item_cart -> {
                navigateToPage(PageEnums.CART)
                true
            }
            else -> {
                false
            }
        }
    }

}
