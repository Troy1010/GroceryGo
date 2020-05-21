package com.example.grocerygo.inheritables

import android.view.View
import com.example.grocerygo.models.Product


interface GGToolbarActivityCallbacks
{
    fun setToolbarAttributes(title: String, hasBackArrow: Boolean? = null)
    fun notifyBadge()
}

interface ActivityHostCallbacks
{
    fun setNavigationEmpty(shouldNavigationBarBeEmpty:Boolean)
    fun goToHome()
    fun goToProfile()
}

interface RecyclerViewCallbacks
{
    fun bindRecyclerItemView(view: View, i: Int)
    fun getRecyclerDataSize() : Int
}