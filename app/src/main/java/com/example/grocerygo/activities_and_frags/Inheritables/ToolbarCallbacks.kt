package com.example.grocerygo.activities_and_frags.Inheritables

interface ToolbarCallbacks
{
    fun setTitle(title:String)
    fun showCart(showCart:Boolean)
    fun showBack(showBack:Boolean)
    fun notifyCartBadge()
}