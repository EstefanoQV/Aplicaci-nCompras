package com.example.compra

object CartManager {
    val cartItems = ArrayList<CartItem>()

    fun calculateSubtotal(): Double {
        var subtotal = 0.0
        for (item in cartItems) {
            subtotal += item.price
        }
        return subtotal
    }
}