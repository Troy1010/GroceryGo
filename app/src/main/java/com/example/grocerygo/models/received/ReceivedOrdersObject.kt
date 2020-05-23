package com.example.grocerygo.models.received

import com.example.grocerygo.models.Order

data class ReceivedOrdersObject(
    val count: Int,
    val `data`: List<Order>,
    val error: Boolean
)
