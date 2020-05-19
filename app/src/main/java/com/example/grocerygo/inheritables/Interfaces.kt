package com.example.grocerygo.inheritables


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