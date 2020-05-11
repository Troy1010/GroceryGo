package com.example.grocerygo.models

data class User (
    val name:String,
    val email:String,
    val password:String
) {
    companion object {
        const val KEY_NAME = "NAME"
        const val KEY_PASSWORD = "PASSWORD"
        const val KEY_EMAIL = "EMAIL"
    }
}