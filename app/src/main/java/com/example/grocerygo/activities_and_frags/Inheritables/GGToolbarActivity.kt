package com.example.grocerygo.activities_and_frags.Inheritables

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuItemCompat
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.*
import com.example.grocerygo.extras.*
import com.example.grocerygo.models.OrderSummary
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.z_cart_icon.view.*

abstract class GGToolbarActivity(override val layout: Int) : TMActivity(layout),
    ToolbarCallbacks {
    abstract val title: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (toolbar_main != null) {
            toolbar_main.title = title
            setSupportActionBar(toolbar_main)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    var toolbarMenu: Menu? = null

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun showBack(showBack: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(showBack)
        supportActionBar?.setHomeButtonEnabled(showBack)
    }

    override fun showCart(showCart: Boolean) {
        toolbarMenu?.findItem(R.id.menu_cart)?.isVisible = showCart
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.three_dot_menu, menu)
        MenuItemCompat.setActionView(menu.findItem(R.id.menu_cart), R.layout.z_cart_icon)
        toolbarMenu = menu
        setupMenuActionListeners()
        notifyCartBadge()
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupMenuActionListeners() {
        toolbarMenu?.findItem(R.id.menu_cart)?.actionView?.setOnClickListener {
            if (this is ActivityHost) {
                bottom_navigation_bar?.selectedItemId = R.id.item_cart
            } else {
                val intent = Intent(this, ActivityHost::class.java)
                intent.putExtra(ActivityHost.KEY_TAB_ID, ActivityHost.TabEnum.Cart.id)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        notifyCartBadge()
        return super.onCreateView(name, context, attrs)
    }

    override fun notifyCartBadge() {
        if (toolbarMenu != null) {
            val cartIconView =
                toolbarMenu?.findItem(R.id.menu_cart)?.actionView
            val quantity = OrderSummary(App.db.getProducts()).totalQuantity
            if (quantity == 0) {
                cartIconView?.text_view_badge?.visibility = View.GONE
                cartIconView?.image_view_badge_oval?.visibility = View.GONE
            } else {
                cartIconView?.text_view_badge?.visibility = View.VISIBLE
                cartIconView?.image_view_badge_oval?.visibility = View.VISIBLE
                cartIconView?.text_view_badge?.text = quantity.toString()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var returning = super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> {
                if (this is ActivityThankYou || this is ActivityChooseTheme) { // TODO this logic is not the best..
                    startActivity(Intent(this, ActivityHost::class.java))
                } else if ((this.supportFragmentManager.backStackEntryCount > 0) or (this !is ActivityHost)) { // TODO probably a more reliable way to check..
                    this.onBackPressed()
                } else {
                    bottom_navigation_bar?.selectedItemId = R.id.item_home
                }
            }
            R.id.menu_cart -> {
                //TODO put the goto cart logic here
                logz("GGToolbarActivity`Selected Cart")
            }
            R.id.menu_order_history -> {
                startActivity(Intent(this, ActivityOrderHistory::class.java))
            }
            R.id.menu_choose_theme -> {
                startActivity(Intent(this, ActivityChooseTheme::class.java))
            }
        }
        return returning
    }

}