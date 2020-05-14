package com.example.grocerygo.activities_and_frags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.grocerygo.R
import com.example.grocerygo.extras.AppCompatActivityWithToolbarFunctionality
import com.example.grocerygo.extras.logz
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_home -> {
                toolbar_top.title = "Home"
                supportFragmentManager.beginTransaction().replace(R.id.frame_page_fragments,FragHome()).commit()
                true
            }
            R.id.item_profile -> {
                toolbar_top.title = "Profile"
                supportFragmentManager.beginTransaction().replace(R.id.frame_page_fragments,FragProfile()).commit()
                true
            }
            else -> {
                false
            }
        }
    }

}
