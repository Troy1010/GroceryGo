package com.example.grocerygo.models

data class User (
    val name:String?,
    val email:String?,
    val password:String?,
    val mobile:String?
) {
    companion object {
        const val KEY_NAME = "NAME"
        const val KEY_PASSWORD = "PASSWORD"
        const val KEY_EMAIL = "EMAIL"
        const val KEY_MOBILE = "MOBILE"
    }
    var loginInfo = LoginObject("","")
        get() {
            return LoginObject(email?:"", password?:"")
        }
}