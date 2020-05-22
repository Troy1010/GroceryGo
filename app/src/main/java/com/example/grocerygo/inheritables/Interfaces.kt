package com.example.grocerygo.inheritables

import android.view.View
import com.example.grocerygo.models.Product


interface ToolbarCallbacks
{
    fun setToolbarAttributes(title: String, hasBackArrow: Boolean? = null)
    fun notifyBadge()
}

interface HostCallbacks
{
    fun setNavigationEmpty(shouldNavigationBarBeEmpty:Boolean)
    fun goToHome()
    fun goToProfile()
}

interface CartRecyclerViewCallbacks
{
    var products: ArrayList<Product>
    fun bindRecyclerItemView(view: View, i: Int)
    fun bindLastRecyclerItemView(view: View, normalItemHeight: Int)
}