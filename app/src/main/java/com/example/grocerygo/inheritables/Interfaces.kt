package com.example.grocerygo.inheritables

import android.view.Menu
import android.view.View
import com.example.grocerygo.models.Product


interface ToolbarCallbacks
{
    fun setTitle(title:String)
    fun showCart(showCart:Boolean)
    fun showBack(showBack:Boolean)
    var toolbarMenu: Menu?
    fun setToolbarAttributes(title: String, hasBackArrow: Boolean? = null)
    fun notifyBadge()
}

interface HostCallbacks
{
    fun showNavigationBar(showNavigationBar:Boolean)
    fun setNavigationEmpty(shouldNavigationBarBeEmpty:Boolean)
    fun goToHome()
    fun goToProfile()
}