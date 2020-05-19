package com.example.grocerygo.inheritables

import android.view.View
import com.example.grocerygo.models.Product


interface GGActivityCallbacks
{
    fun setToolbarAttributes(title: String, hasBackArrow: Boolean? = null)
}

interface ActivityHostCallbacks
{
    fun setNavigationEmpty(shouldNavigationBarBeEmpty:Boolean)
    fun goToHome()
    fun goToProfile()
}

interface RecyclerViewActivityCallbacks
{
    fun bindRecyclerItemView(itemView: View, position: Int)
}