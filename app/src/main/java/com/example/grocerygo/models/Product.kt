package com.example.grocerygo.models

import java.io.Serializable

data class Product(
    val __v: Int =0 ,
    val _id: String="",
    val catId: Int=0,
    val created: String="",
    val description: String="",
    val image: String="",
    val mrp: Double=0.0,
    val position: Int=0,
    val price: Double=0.0,
    val productName: String="PRODUCTNAME",
    var quantity: Int=0,
    val status: Boolean=false,
    val subId: Int=0,
    val unit: String="",
    var sqlID: Int=0
) : Serializable