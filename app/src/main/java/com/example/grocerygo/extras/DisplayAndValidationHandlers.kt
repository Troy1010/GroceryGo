package com.example.grocerygo.extras

class DisplayAndValidationHandlers {

}

fun DisplayMoney(value:Double):String {
    return ("$" + "%.2f".format(value.round(2)))
}





enum class Validator {
    Name {
        override fun invoke(): String {
            return "qwer"
        }
    };
    abstract operator fun invoke(): String
}