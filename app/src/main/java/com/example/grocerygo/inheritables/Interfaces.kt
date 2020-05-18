package com.example.grocerygo.inheritables


interface GGActivityCallbacks
{
    fun setToolbarAttributes(title: String, hasBackArrow: Boolean? = null)
}

interface ActivityHostCallbacks
{
    fun goToHome()
    fun goToProfile()
}