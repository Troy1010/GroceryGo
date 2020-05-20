package com.example.grocerygo.models

class OrderSummary(
    val quantityTotal: Int,
    val priceTotal: Double,
    val fakePriceTotal: Double
) {
    //    companion object {
//        val baseDeliveryFee = 16.50
//        val deliveryFee:Double
//            get() = if (priceTotal > 300) baseDeliveryFee else 0.0
//    } // TODO refactor this in

    fun getDiscount():Double {
        return ((fakePriceTotal-priceTotal)/fakePriceTotal)*fakePriceTotal // TODO is this the right formula?
    }

    fun getDeliveryFee():Double {
        val baseDeliveryFee = 16.50
        return if (priceTotal > 300) baseDeliveryFee else 0.0
    }
    fun getGrandTotal():Double {
        return getDeliveryFee() + priceTotal
    }
}