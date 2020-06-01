package com.example.grocerygo.extras

fun DisplayMoney(value: Double): String {
    return ("$" + "%.2f".format(value.round(2)))
}

class InputValidation {
    companion object {
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
