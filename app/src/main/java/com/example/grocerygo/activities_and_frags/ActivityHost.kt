package com.example.grocerygo.activities_and_frags

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.HostCallbacks
import com.example.grocerygo.extras.App
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.extras.logz
import com.example.grocerygo.models.Product
import kotlinx.android.synthetic.main.activity_host.*

class ActivityHost : GGToolbarActivity(layout = R.layout.activity_host),
    HostCallbacks {
    override val title = "Host"
    var tabID:Int? = null
        get() {
            var returning = intent?.getIntExtra(KEY_TAB_ID, -1)
            if (returning == -1) {
                returning = null
            }
            intent?.removeExtra(KEY_TAB_ID)
            return returning
        }

    companion object {
        const val KEY_TAB_ID = "TAB_ID"

        @JvmStatic
        fun start(context: Context, eTab: TabEnum? = null) {
            val intent = Intent(context, ActivityHost::class.java)
            if (eTab != null) {
                intent.putExtra(KEY_TAB_ID, eTab.id)
            }
//            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigationBar()
    }

    override fun onStart() {
        super.onStart()
        val zzz = tabID // TODO this logic could be much better
//        logz("ActivityHost`onStart`tabID:${zzz}")
        if (zzz != null) {
            val tab = getTabEnumfromID(zzz!!) ?: TabEnum.Home
            goToTab(tab)
        }
    }

    override fun showNavigationBar(showNavigationBar: Boolean) {
        if (showNavigationBar) {
            bottom_navigation_bar.visibility = View.VISIBLE
        } else {
            bottom_navigation_bar.visibility = View.GONE
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

    override fun goToTab(eTab: TabEnum) {
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

    enum class TabEnum(val id: Int) {
        Home(R.id.item_home), Search(R.id.item_search), Cart(R.id.item_cart), Profile(R.id.item_profile)
    }

    fun getTabEnumfromID(id: Int): TabEnum? {
        for (enum in TabEnum.values()) {
            if (enum.id == id) {
                return enum
            }
        }
        return null
    }

}
