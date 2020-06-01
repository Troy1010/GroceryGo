package com.example.grocerygo.extras

import com.example.tmcommonkotlin.logz

fun DisplayMoney(value: Double): String {
    return ("$" + "%.2f".format(value.round(2)))
}

class InputValidation {
    companion object {
        val asStreetAddress = { streetAddress :String ->
            if (streetAddress.isEmpty()) {
                Result.Error("Required")
            } else if (!Regex("""[0-9][0-9]*""").containsMatchIn(streetAddress)) {
                Result.Error("Must have a number")
            } else if (!Regex("""[0-9][0-9]*..*""").containsMatchIn(streetAddress.trim())) {
                Result.Error("Must have a street name")
            } else {
                val numMatch = Regex("""[0-9][0-9]*""").find(streetAddress)
                val streetNameMatch = Regex("""..*""").find(streetAddress, (numMatch?.range?.last?:-1)+1)
                Result.Success(numMatch?.value + " " + streetNameMatch?.value?.trim()?.capitalize()?.noDoubleSpaces())
            }
        }
        val asAptNum = { aptNum:String ->
            if (Regex(""".\s.""").containsMatchIn(aptNum)) {
                Result.Error("Must not contain spaces")
            } else {
                Result.Success(aptNum.trim())
            }
        }
        val asCity = { city :String ->
            if (city.isEmpty()) {
                Result.Error("Required")
            } else if (Regex(""".\s.""").containsMatchIn(city)) {
                Result.Error("Must not contain spaces")
            } else {
                Result.Success(city.trim().capitalize())
            }
        }
        val asState = { state :String ->
            if (state.isEmpty()) {
                Result.Error("Required")
            } else if (!Regex("""[A-z][A-z]""").matches(state)) {
                Result.Error("Must contain two letters")
            } else {
                Result.Success(state.trim().toUpperCase())
            }
        }
        val asZipCode = { zipCode :String ->
            if (zipCode.isEmpty()) {
                Result.Error("Required")
            } else if (!Regex("""[A-z]""").containsMatchIn(zipCode)) {
                Result.Error("Must not contain letters")
            } else {
                Result.Success(zipCode.trim())
            }
        }
        val asName = { name: String ->
            if (name.isEmpty()) {
                Result.Error("Required")
            } else if (name.length < 2) {
                Result.Warning("Name seems too short")
            } else {
                Result.Success(name.capitalize())
            }
        }
        val asEmail = { email: String ->
            if (email.isEmpty()) {
                Result.Error("Required")
            } else if (!Regex("""@..*\.com\s*${'$'}""").containsMatchIn(email)) {
                Result.Error("Must contain an email domain")
            } else if (!Regex("""..*@..*\.com\s*${'$'}""").containsMatchIn(email)) {
                Result.Error("Must contain an email name")
            } else {
                Result.Success(email)
            }
        }
        val asPassword = { password: String ->
            if (password.isEmpty()) {
                Result.Error("Required")
            } else if (password.length < 6) {
                Result.Error("Must have at least 6 characters")
            } else if (!password.hasDigit()) {
                Result.Error("Must contain at least 1 digit")
            } else if (password == password.toLowerCase()) {
                Result.Warning("It is recommended that passwords have at least 1 uppercase character")
            } else {
                Result.Success(password)
            }
        }
        val asPhone = { phone: String ->
            if (phone.isEmpty()) {
                Result.Error("Required")
            } else if (!(phone.isAllDigits())) {
                Result.Error("Must only contain digits")
            } else if (phone.length != 10) {
                Result.Error("Must have 10 characters")
            } else {
                Result.Success(phone)
            }
        }
    }


    sealed class Result {
        data class Error(val msg: String) : Result()
        data class Warning(val msg: String) : Result()
        data class Success(val correctedValue: String) : Result()

        //
        inline fun ifError(errorLambda: (errorMsg: String) -> Unit): Result {
            if (this is Error) {
                errorLambda(this.msg)
            }
            return this
        }

        inline fun ifWarning(warningLambda: (warningMsg: String) -> Unit): Result {
            if (this is Warning) {
                warningLambda(this.msg)
            }
            return this
        }

        inline fun ifSuccess(successLambda: () -> Unit): Result {
            if (this is Success) {
                successLambda()
            }
            return this
        }
    }
}
