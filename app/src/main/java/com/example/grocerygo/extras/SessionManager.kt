package com.example.grocerygo.extras

import android.content.Context
import com.example.grocerygo.models.Address
import com.example.grocerygo.models.User
import com.google.gson.Gson

class SessionManager {
    private var sharedPref = App.instance.getSharedPreferences(
        Config.SHARED_PREF_FILE_NAME,
        Context.MODE_PRIVATE
    )
    private var editor = sharedPref.edit()
    var primaryAddress: Address?
        get() {
            val storedPrimaryAddress = sharedPref.getString(KEY_PRIMARY_ADDRESS, null)
            return if (storedPrimaryAddress == null) null else {
                Gson().fromJson(storedPrimaryAddress, Address::class.java)
            }
        }
        set(value) {
            if (value == null) {
                editor.remove(KEY_PRIMARY_ADDRESS)
            } else {
                editor.putString(KEY_PRIMARY_ADDRESS, Gson().toJson(value))
            }
            editor.commit()
        }

    var user: User?
        get() {
            val storedName = sharedPref.getString(User.KEY_NAME, null)
            val storedEmail = sharedPref.getString(User.KEY_EMAIL, null)
            val storedPassword = sharedPref.getString(User.KEY_PASSWORD, null)
            val storedMobile = sharedPref.getString(User.KEY_MOBILE, null)
            val storedID = sharedPref.getString(User.KEY_ID, null)
            return User(
                storedName,
                storedEmail,
                storedPassword,
                storedMobile,
                _id = storedID
            )
        }
        set(value) {
            editor.putString(User.KEY_EMAIL, value.email)
            editor.putString(User.KEY_NAME, value.name)
            editor.putString(User.KEY_PASSWORD, value.password)
            editor.putString(User.KEY_MOBILE, value.mobile)
            editor.putString(User.KEY_ID, value._id)
            editor.commit()
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
}