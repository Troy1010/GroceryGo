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
import com.example.grocerygo.extras.PageEnums
import com.example.grocerygo.extras.logz
import kotlinx.android.synthetic.main.app_toolbar.*

abstract class GGToolbarActivity: TMActivity(), GGActivityCallbacks {
    abstract val title:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logz("GGToolbarActivity`about to set up toolbar")
        setSupportActionBar(toolbar_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        logz("GGToolbarActivity`finished setting up toolbar")
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
                if ((this.supportFragmentManager.backStackEntryCount > 0) or (this !is ActivityHost) ){ // TODO probably a more reliable way to check..
                    this.onBackPressed()
                } else {
                    this.navigateToPage(PageEnums.HOME)
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
        }
        return returning
    }

    override fun setToolbarTitle(title:String) {
        logz("setToolbarTitle`title:$title")
        toolbar_main.title = title
        logz("setToolbarTitle`toolbar_main.title:${toolbar_main.title}")
    }


    fun navigateToPage(e: PageEnums) {
        val frag = when (e) {
            PageEnums.SEARCH -> FragSearchPrimary()
            PageEnums.PROFILE -> FragProfile()
            PageEnums.CART -> FragCart()
            else -> FragHome()
        }
        supportFragmentManager.beginTransaction().replace(R.id.frame_fragments, frag).commit()
    }

}