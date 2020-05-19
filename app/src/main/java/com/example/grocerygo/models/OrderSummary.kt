package com.example.grocerygo.models

data class OrderSummary(
    val quantityTotal: Int,
    val priceTotal: Double,
    val fakePriceTotal: Double
)