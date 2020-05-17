package com.example.grocerygo.inheritables

interface Title
{
    val title:String
}

interface GGActivityCallbacks
{
    fun setToolbarTitle(title: String)
}