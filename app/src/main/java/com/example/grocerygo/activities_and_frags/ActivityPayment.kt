package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.InputValidation
import com.example.grocerygo.extras.handleResult
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_payment.*

class ActivityPayment: GGToolbarActivity(layout = R.layout.activity_payment),
    View.OnFocusChangeListener {
    override val title = "Payment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        text_input_card_number.setOnFocusChangeListener(this)
        text_input_cardholder_name.setOnFocusChangeListener(this)
        text_input_expiration_date.setOnFocusChangeListener(this)
        button_payment_submit.setOnClickListener {

            var areAnyErrors = handleResult(
                InputValidation.asCardNumber(text_input_card_number.text.toString()),
                text_input_layout_card_number)
            areAnyErrors = handleResult(
                InputValidation.asName(text_input_cardholder_name.text.toString()),
                text_input_layout_cardholder_name) || areAnyErrors
            areAnyErrors = handleResult(
                InputValidation.asExpirationDate(text_input_expiration_date.text.toString()),
                text_input_layout_expiration_date) || areAnyErrors
            if (!areAnyErrors) {
                App.sm.displayPayment = hidePayment(text_input_card_number.text.toString())
                onBackPressed()
                finish()
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v) {
            text_input_card_number -> handleResult(
                InputValidation.asCardNumber(text_input_card_number.text.toString()),
                text_input_layout_card_number, hasFocus
            )
            text_input_cardholder_name -> handleResult(
                InputValidation.asName(text_input_cardholder_name.text.toString()),
                text_input_layout_cardholder_name, hasFocus
            )
            text_input_expiration_date -> handleResult(
                InputValidation.asExpirationDate(text_input_expiration_date.text.toString()),
                text_input_layout_expiration_date, hasFocus
            )
        }
    }
}


fun hidePayment(s: String):String? {
    return if (s.length < 4) {
        null
    } else {
        "**** **** **** ${s.takeLast(4)}"
    }
}