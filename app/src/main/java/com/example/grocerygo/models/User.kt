package com.example.grocerygo.models

data class User (
    val name:String?,
    val email:String?,
    val password:String?,
    val mobile:String?,
    val primaryAddress:Address?=null,
    val id:String? = null
) {
    companion object {
        const val KEY_NAME = "NAME"
        const val KEY_PASSWORD = "PASSWORD"
        const val KEY_EMAIL = "EMAIL"
        const val KEY_MOBILE = "MOBILE"
        const val KEY_ID = "ID"
        const val KEY_PRIMARY_ADDRESS = "PRIMARY_ADDRESS"
    }
    var loginInfo = LoginObject("","")
        get() {
            return LoginObject(email?:"", password?:"")
        }
}