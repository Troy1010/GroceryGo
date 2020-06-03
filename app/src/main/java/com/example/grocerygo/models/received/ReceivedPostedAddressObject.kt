package com.example.grocerygo.models.received

import com.example.grocerygo.models.Address

data class ReceivedPostedAddressObject(
    val `data`: Address,
    val error: Boolean,
    val message: String
)