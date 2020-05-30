package com.example.grocerygo.extras

class DisplayAndValidationHandlers {

}

fun DisplayMoney(value:Double):String {
    return ("$" + "%.2f".format(value.round(2)))
}





enum class Validator(val id: Int, val lambda: ()->String) {
    Name(0, {
        "qwer"
    })
}