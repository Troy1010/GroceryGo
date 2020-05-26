package com.example.grocerygo.extras

import android.content.Context
import com.example.grocerygo.R
import com.example.grocerygo.models.Address
import com.example.grocerygo.models.User
import com.google.gson.Gson

class SessionManager {
    companion object {
        const val KEY_THEME = "THEME"
    }

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
    var theme: Int
        get() {
            val storedThemeInt = sharedPref.getInt(KEY_THEME, -1)
            return if (storedThemeInt == -1) {
                R.style.GroceryGoLight_DefaultTheme
            } else {
                storedThemeInt
            }
        }
        set(value) {
            editor.putInt(KEY_THEME, value)
            editor.commit()
        }

    var user: User?
        get() {
            val storedName = sharedPref.getString(User.KEY_NAME, null)
            val storedEmail = sharedPref.getString(User.KEY_EMAIL, null)
            val storedPassword = sharedPref.getString(User.KEY_PASSWORD, null)
            val storedMobile = sharedPref.getString(User.KEY_MOBILE, null)
            val storedID = sharedPref.getString(User.KEY_ID, null)
            return if ((storedEmail == null) or (storedPassword == null) or (storedID == null)) {
                null
            } else {
                User(
                    storedName ?: "",
                    storedEmail ?: "",
                    storedPassword ?: "",
                    storedMobile ?: "",
                    _id = storedID
                )
            }
        }
        set(value) {
            if (value == null) {
                reset()
                editor.clear()
            } else {
                editor.putString(User.KEY_EMAIL, value.email)
                editor.putString(User.KEY_NAME, value.name)
                editor.putString(User.KEY_PASSWORD, value.password)
                editor.putString(User.KEY_MOBILE, value.mobile)
                editor.putString(User.KEY_ID, value._id)
            }
            editor.commit()
        }

    fun reset() {
        primaryAddress = null
        App.db.clear()
    }


    fun isLoggedIn(): Boolean {
        return user != null
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }
}
