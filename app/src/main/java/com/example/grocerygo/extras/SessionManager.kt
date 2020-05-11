package com.example.grocerygo.extras

import android.content.Context
import com.example.grocerygo.app.App
import com.example.grocerygo.models.User

class SessionManager {
    private var sharedPref = App.instance.getSharedPreferences(Config.SHARED_PREF_FILE_NAME,
        Context.MODE_PRIVATE)
    private var editor = sharedPref.edit()
    fun attemptLogin (user: User) :Boolean{
        var storedEmail = sharedPref.getString(User.KEY_EMAIL, null)
        var storedPassword = sharedPref.getString((User.KEY_PASSOWRD), null)
        return when {
            storedEmail == null -> {
                false
            }
            storedPassword == null -> {
                false
            }
            else -> {
                (user.email == storedEmail) and (user.password == storedPassword)
            }
        }
    }
    fun register (user:User) {
        // store user in sharedPref
        editor.putString(User.KEY_EMAIL, user.email)
        editor.putString(User.KEY_NAME, user.name)
        editor.putString(User.KEY_PASSOWRD, user.password)
        editor.commit()
    }
    fun isLoggedIn() : Boolean {
        var storedEmail = sharedPref.getString(User.KEY_EMAIL, null)
        var storedPassword = sharedPref.getString((User.KEY_PASSOWRD), null)
        return !((storedEmail == null )or (storedPassword == null))
    }
}