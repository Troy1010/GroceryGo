package com.example.grocerygo.models

data class Order(
    val __v: Int? = null,
    val _id: String? = null,
    val date: String? = null,
    val orderStatus: String,
    val orderSummary: OrderSummary_PASSABLE,
    val payment: Payment? = null, // TODO
    val products: List<Product>,
    val shippingAddress: Address,
    val user: User,
    val userId: String
)

data class OrderSummary_PASSABLE(
    val _id: String? = null,
    val deliveryCharges: Double,
    val discount: Int? =null,
    val orderAmount: Int? =null,
    val ourPrice: Int?=null,
    val totalAmount: Double
)