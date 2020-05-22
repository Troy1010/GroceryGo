package com.example.grocerygo.models

data class ReceivedAddressesObject(
    val count: Int,
    val `data`: List<Address>,
    val error: Boolean
)