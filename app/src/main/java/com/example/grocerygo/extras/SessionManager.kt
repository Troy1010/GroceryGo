package com.example.grocerygo.extras

import android.content.Context
import com.example.grocerygo.models.LoginObject
import com.example.grocerygo.models.User

class SessionManager {
    private var sharedPref = App.instance.getSharedPreferences(
        Config.SHARED_PREF_FILE_NAME,
        Context.MODE_PRIVATE
    )
    private var editor = sharedPref.edit()
    var user: User
        get() {
            var storedName = sharedPref.getString(User.KEY_NAME, null)
            var storedEmail = sharedPref.getString(User.KEY_EMAIL, null)
            var storedPassword = sharedPref.getString(User.KEY_PASSWORD, null)
            var storedMobile = sharedPref.getString(User.KEY_MOBILE, null)
            var storedID = sharedPref.getString(User.KEY_ID, null)
            return User(storedName, storedEmail, storedPassword, storedMobile, storedID)
        }
        set(value) {
            editor.putString(User.KEY_EMAIL, value.email)
            editor.putString(User.KEY_NAME, value.name)
            editor.putString(User.KEY_PASSWORD, value.password)
            editor.putString(User.KEY_MOBILE, value.mobile)
            editor.putString(User.KEY_ID, value.id)
            editor.commit()
        }


    fun attemptLogin(loginObject: LoginObject): Boolean {
        return if ((loginObject.email == "") || (loginObject.password == "")) {
            false
        } else {
            (loginObject.email == user.email) and (loginObject.password == user.password)
        }
    }

    fun isLoggedIn(): Boolean {
        var storedEmail = sharedPref.getString(User.KEY_EMAIL, null)
        var storedPassword = sharedPref.getString((User.KEY_PASSWORD), null)
        return !((storedEmail == null) or (storedEmail == "") or (storedPassword == null) or (storedPassword == ""))
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }
}