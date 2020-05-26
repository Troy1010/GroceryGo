package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.View
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.HostCallbacks
import com.example.grocerygo.extras.App
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.extras.logz
import kotlinx.android.synthetic.main.activity_host.*

class ActivityHost : GGToolbarActivity(layout = R.layout.activity_host),
    HostCallbacks {
    override val title = "Host"
    val tabID by lazy { intent?.getIntExtra(KEY_TAB_ID, TabEnum.Home.id)?:TabEnum.Home.id }

    companion object {
        const val KEY_TAB_ID = "TAB_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigationBar()
    }

    override fun onStart() {
        super.onStart()
        goToTab(getTabEnumfromID(tabID)?:TabEnum.Home)
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

    fun goToTab(eTab:TabEnum) {
        bottom_navigation_bar.selectedItemId = eTab.id
    }

    override fun goToHome() {
        goToTab(TabEnum.Home)
//        bottom_navigation_bar.selectedItemId = R.id.item_home
    }

    override fun goToProfile() {
        goToTab(TabEnum.Profile)
//        bottom_navigation_bar.selectedItemId = R.id.item_profile
    }

    enum class TabEnum(val id:Int) {
        Home(R.id.item_home), Search(R.id.item_search), Cart(R.id.item_cart), Profile(R.id.item_profile)
    }
    fun getTabEnumfromID(id:Int) : TabEnum? {
        for (enum in TabEnum.values()) {
            if (enum.id == id) {
                return enum
            }
        }
        return null
    }

}
