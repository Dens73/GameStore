package com.gamestore.app.utils

import com.gamestore.app.models.Order
import com.gamestore.app.models.OrderStatus
import java.text.SimpleDateFormat
import java.util.*

object OrderManager {
    
    private val orders = mutableListOf<Order>()
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("ru"))
    
    fun createOrder(userId: String, cartItems: List<com.gamestore.app.models.CartItem>, totalAmount: Double): Order {
        val orderId = "ORD_${System.currentTimeMillis()}"
        val order = Order(
            id = orderId,
            userId = userId,
            items = cartItems.toList(),
            totalAmount = totalAmount,
            orderDate = dateFormat.format(Date()),
            status = OrderStatus.COMPLETED
        )
        orders.add(order)
        return order
    }
    
    fun getUserOrders(userId: String): List<Order> {
        return orders.filter { it.userId == userId }.sortedByDescending { it.orderDate }
    }
    
    fun clearOrders() {
        orders.clear()
    }
}