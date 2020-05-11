package com.example.grocerygo.extras

import android.content.Context
import android.util.Log
import com.example.grocerygo.app.App
import com.example.grocerygo.models.LoginObject
import com.example.grocerygo.models.User

class SessionManager {
    private var sharedPref = App.instance.getSharedPreferences(
        Config.SHARED_PREF_FILE_NAME,
        Context.MODE_PRIVATE
    )
    private var editor = sharedPref.edit()
    fun attemptLogin(loginObject: LoginObject): Boolean {
        var storedEmail = sharedPref.getString(User.KEY_EMAIL, null)
        var storedPassword = sharedPref.getString(User.KEY_PASSWORD, null)
        return when {
            storedEmail == null -> {
                false
            }
            storedPassword == null -> {
                false
            }
            else -> {
                (loginObject.email == storedEmail) and (loginObject.password == storedPassword)
            }
        }
    }

    fun register(user: User) {
        // store user in sharedPref
        editor.putString(User.KEY_EMAIL, user.email)
        editor.putString(User.KEY_NAME, user.name)
        editor.putString(User.KEY_PASSWORD, user.password)
        editor.commit()
    }

    fun isLoggedIn(): Boolean {
        var storedEmail = sharedPref.getString(User.KEY_EMAIL, null)
        var storedPassword = sharedPref.getString((User.KEY_PASSWORD), null)
        return !((storedEmail == null) or (storedEmail == "") or (storedPassword == null) or (storedPassword == ""))
    }

    var user = User("", "", "")
        get() {
            var storedName = sharedPref.getString(User.KEY_NAME, null)
            var storedEmail = sharedPref.getString(User.KEY_EMAIL, null)
            var storedPassword = sharedPref.getString(User.KEY_PASSWORD, null)
            return User(storedName?:"", storedEmail?: "", storedPassword?: "")
        }
    fun logout() {
        editor.clear()
        editor.commit()
    }
}