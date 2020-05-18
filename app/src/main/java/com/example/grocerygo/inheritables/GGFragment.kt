package com.example.grocerygo.inheritables

abstract class GGFragment : TMFragment() {
    abstract val title: String


    override fun onStart() {
        super.onStart()
        (activity as GGActivityCallbacks).setToolbarAttributes(title, true)
    }
}