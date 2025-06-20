package com.gamestore.app.utils

import com.gamestore.app.models.BalanceTransaction
import com.gamestore.app.models.TransactionType
import java.text.SimpleDateFormat
import java.util.*

object BalanceManager {
    
    private val transactions = mutableListOf<BalanceTransaction>()
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("ru"))
    
    fun addTransaction(amount: Double, type: TransactionType, description: String) {
        val currentUser = AuthManager.getCurrentUser() ?: return
        
        val transaction = BalanceTransaction(
            id = "TXN_${System.currentTimeMillis()}",
            userId = currentUser.id,
            amount = amount,
            type = type,
            description = description,
            date = dateFormat.format(Date())
        )
        transactions.add(transaction)
    }
    
    fun getUserTransactions(userId: String): List<BalanceTransaction> {
        return transactions.filter { it.userId == userId }.sortedByDescending { it.date }
    }
}