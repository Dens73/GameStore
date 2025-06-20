package com.gamestore.app.utils

import com.gamestore.app.models.User

object AuthManager {
    
    private var currentUser: User? = null
    private var isLoggedIn = false
    
    // Предустановленный пользователь для демонстрации
    private val demoUser = User(
        id = "GS_001337",
        username = "user",
        email = "user@gamestore.com",
        registrationDate = "15 января 2024",
        balance = 10000.0
    )
    
    fun login(username: String, password: String): Boolean {
        return if (username == "user" && password == "user") {
            currentUser = demoUser
            isLoggedIn = true
            true
        } else {
            false
        }
    }
    
    fun logout() {
        currentUser = null
        isLoggedIn = false
        CartManager.clearCart()
        OrderManager.clearOrders()
    }
    
    fun isLoggedIn(): Boolean = isLoggedIn
    
    fun getCurrentUser(): User? = currentUser
    
    fun updateBalance(newBalance: Double) {
        currentUser?.let { user ->
            currentUser = user.copy(balance = newBalance)
        }
    }
    
    fun addBalance(amount: Double) {
        currentUser?.let { user ->
            val newBalance = user.balance + amount
            currentUser = user.copy(balance = newBalance)
            BalanceManager.addTransaction(
                amount = amount,
                type = com.gamestore.app.models.TransactionType.DEPOSIT,
                description = "Пополнение баланса"
            )
        }
    }
    
    fun deductBalance(amount: Double): Boolean {
        currentUser?.let { user ->
            return if (user.balance >= amount) {
                val newBalance = user.balance - amount
                currentUser = user.copy(balance = newBalance)
                true
            } else {
                false
            }
        }
        return false
    }
}