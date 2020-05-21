package com.example.grocerygo.extras

class DisplayAndValidationHandlers {

}

fun DisplayMoney(value:Double):String {
    return ("$" + value.round(2).toString())
}