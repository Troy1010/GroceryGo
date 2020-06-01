package com.example.grocerygo.extras

import com.example.grocerygo.R
import com.example.tmcommonkotlin.InputValidation
import com.example.tmcommonkotlin.logz
import com.google.android.material.textfield.TextInputLayout



fun handleResult(
    validationResult: InputValidation.Result,
    layout: TextInputLayout,
    bClearError: Boolean = false
): Boolean {
    if (bClearError) {
        layout.isErrorEnabled = false
        return false
    }
    return when (validationResult) {
        is InputValidation.Result.Error -> {
            layout.setErrorTextAppearance(R.style.ErrorText)
            layout.error = validationResult.msg
            true
        }
        is InputValidation.Result.Warning -> {
            layout.setErrorTextAppearance(R.style.WarningText)
            layout.error = validationResult.msg
            false
        }
        is InputValidation.Result.Success -> {
            layout.editText?.setText(validationResult.correctedValue)
            layout.isErrorEnabled = false
            false
        }
    }
}


fun DisplayMoney(value: Double): String {
    return ("$" + "%.2f".format(value.round(2)))
}