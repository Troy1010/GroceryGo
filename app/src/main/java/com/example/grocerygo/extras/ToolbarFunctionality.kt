package com.example.grocerygo.extras

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatCallback
import androidx.fragment.app.Fragment
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.*
import kotlinx.android.synthetic.main.app_toolbar.*

abstract class AppCompatActivityWithToolbarFunctionality : AppCompatActivity(), AppCompatCallback {


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.three_dot_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if ((supportFragmentManager.backStackEntryCount > 0) or (this !is ActivityHost) ){ // TODO probably a more reliable way to check..
                    onBackPressed()
                } else {
                    navigateToPage(PageEnums.HOME)
                }
            }
            R.id.menu_cart -> {
                Log.d("TMLog", "OptionsMenu`Selected Cart")
            }
            R.id.menu_profile -> {
                startActivity(Intent(this, FragProfile::class.java))
            }
            R.id.menu_settings -> {
                Log.d("TMLog", "OptionsMenu`Selected Settings")
            }
        }
        return true
    }


    // TODO refactor this OUT

    fun navigateToPage(e: PageEnums) {
        when (e) {
            PageEnums.SEARCH -> usualNavigation(FragSearchPrimary())
            PageEnums.PROFILE -> usualNavigation(FragProfile())
            PageEnums.CART -> usualNavigation(FragCart())
            else -> usualNavigation(FragHome())
        }
    }

    private fun usualNavigation(frag: Fragment) {
        if (frag is Title) {
            toolbar_top.title = frag.title
        }
        supportFragmentManager.beginTransaction().replace(R.id.frame_fragments, frag).commit()

    }
}