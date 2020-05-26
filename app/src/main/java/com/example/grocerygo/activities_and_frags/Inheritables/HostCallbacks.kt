package com.example.grocerygo.activities_and_frags.Inheritables

import com.example.grocerygo.activities_and_frags.ActivityHost

interface HostCallbacks
{
    fun showNavigationBar(showNavigationBar:Boolean)
    fun goToTab(eTab: ActivityHost.TabEnum)
    fun goToHome()
    fun goToProfile()
}