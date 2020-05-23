package com.example.grocerygo.models

class OrderSummary(products: ArrayList<Product>) {
    constructor(products:List<Product>):this(ArrayList(products))

    val totalQuantity: Int
    val totalPrice: Double
    val totalFakePrice: Double

    init {
        var totalQuantityVar = 0
        var totalPriceVar = 0.0
        var totalFakePriceVar = 0.0
        for (product in products) {
            totalQuantityVar += product.quantity
            totalPriceVar += product.quantity * product.price
            totalFakePriceVar += product.quantity * product.mrp
        }
        totalQuantity = totalQuantityVar
        totalPrice = totalPriceVar
        totalFakePrice = totalFakePriceVar
    }
    var deliveryFee = 0.0
        get() {
            val baseDeliveryFee = 16.50
            return if (totalPrice > 300) baseDeliveryFee else 0.0
        }
    var grandTotal = 0.0
        get() = deliveryFee + tax + totalPrice
    var fakeDiscount = 0.0
        get() = totalFakePrice - totalPrice
    var fakeDiscountPercentage = 0.0
        get() = fakeDiscount/totalFakePrice
    var tax = 0.0
        get() {
            val taxMult = 0.08
            return totalPrice * taxMult
        }
}