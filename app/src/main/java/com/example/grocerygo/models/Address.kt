package com.example.grocerygo.models


data class Address(
    val __v: Int,
    val _id: String,
    val city: String,
    val houseNo: String,
    val location: String,
    val mobile: String,
    val name: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
) {
    var displayableStreetAddress = ""
        get() = "$houseNo $streetName"
    var displayableFullAddress = ""
        get() {
            return "$displayableStreetAddress\n$city, $location $pincode"
        }
}