package com.example.grocerygo.inheritables

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.*
import com.example.grocerygo.extras.*
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.app_toolbar.*

abstract class GGToolbarActivity : TMActivity(), GGActivityCallbacks {
    abstract val title: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar_main.title = title
        setSupportActionBar(toolbar_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var returning = super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.three_dot_menu, menu)
        return returning
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var returning = super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> {
                if ((this.supportFragmentManager.backStackEntryCount > 0) or (this !is ActivityHost)) { // TODO probably a more reliable way to check..
                    this.onBackPressed()
                } else {
                    bottom_navigation_bar.selectedItemId = R.id.item_home // TODO probably shouldn't reference bottom navigation bar directly..
                }
            }
            R.id.menu_cart -> {
                logz("GGToolbarActivity`Selected Cart")
            }
            R.id.menu_profile -> {
                var intent = Intent(this, FragProfile::class.java)
                this.startActivity(intent)
            }
            R.id.menu_settings -> {
                logz("GGToolbarActivity`Selected Settings")
            }
            R.id.menu_printList -> {
                logz(App.db.getProducts().narrate())
            }
            R.id.menu_setTitle -> {
//                setToolbarTitle("TestTitle")
            }
        }
        return returning
    }

    override fun setToolbarAttributes(title: String, hasBackArrow: Boolean?) {
        toolbar_main.title = title
        if (hasBackArrow != null) {
            this.supportActionBar?.setDisplayHomeAsUpEnabled(hasBackArrow)
        }
    }

}