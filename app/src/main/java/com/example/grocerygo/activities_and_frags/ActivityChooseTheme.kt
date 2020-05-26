package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.logz
import kotlinx.android.synthetic.main.activity_choose_theme.*

class ActivityChooseTheme : GGToolbarActivity(R.layout.activity_choose_theme), View.OnClickListener {
    override val title = "Choose Theme"
    private val themeButtonMap = HashMap<RadioButton, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // define themeButtonMap
        themeButtonMap[radio_button_one] = R.style.GroceryGoLight_DefaultTheme
        themeButtonMap[radio_button_two] = R.style.GroceryGoLight_BlueTheme
        themeButtonMap[radio_button_three] = R.style.GroceryGoLight_PurpleTheme
        // check the theme we have
        for ((radio_button, themeInt) in themeButtonMap) {
            if (themeInt == App.sm.theme) {
                radio_group_choose_theme.check(radio_button.id)
                break
            }
        }
        setupListeners()
    }

    private fun setupListeners() {
        for (radioButton in themeButtonMap.keys) {
            radioButton.setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        if (v is RadioButton && v in themeButtonMap) {
            App.sm.theme = themeButtonMap[v]!!
            val intent = Intent(this, ActivityChooseTheme::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}