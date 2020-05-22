package com.example.grocerygo.models

data class ReceivedLoginObject(
    val token: String,
    val user: UserZ
)

data class UserZ(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
)