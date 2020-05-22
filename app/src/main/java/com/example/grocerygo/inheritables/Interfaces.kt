package com.example.grocerygo.inheritables

import android.view.Menu
import android.view.View
import com.example.grocerygo.models.Product


interface ToolbarCallbacks
{
    fun setTitle(title:String)
    fun showCart(showCart:Boolean)
    fun showBack(showBack:Boolean)
    fun notifyCartBadge()
}

interface HostCallbacks
{
    fun showNavigationBar(showNavigationBar:Boolean)
    fun goToHome()
    fun goToProfile()
}