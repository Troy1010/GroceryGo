package com.example.grocerygo.models.received

data class ReceivedPostedAddressObject(
    val `data`: DataZ,
    val error: Boolean,
    val message: String
)

data class DataZ(
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
)