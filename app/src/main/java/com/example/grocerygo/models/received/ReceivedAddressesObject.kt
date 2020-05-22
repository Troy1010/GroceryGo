package com.example.grocerygo.models.received

import com.example.grocerygo.models.Address

data class ReceivedAddressesObject(
    val count: Int,
    val `data`: List<Address>,
    val error: Boolean
)