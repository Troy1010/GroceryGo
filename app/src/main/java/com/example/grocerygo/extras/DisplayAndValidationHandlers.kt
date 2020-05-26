package com.example.grocerygo.extras

class DisplayAndValidationHandlers {

}

fun DisplayMoney(value:Double):String {
    return ("$" + "%.2f".format(value.round(2)))
}