package com.gamestore.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gamestore.app.databinding.ItemOrderBinding
import com.gamestore.app.models.Order
import com.bumptech.glide.Glide

class OrderAdapter : ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class OrderViewHolder(
        private val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(order: Order) {
            binding.apply {
                textOrderId.text = "Заказ ${order.id}"
                textOrderDate.text = order.orderDate
                textOrderTotal.text = "${order.totalAmount.toInt()} ₽"
                textOrderStatus.text = when (order.status) {
                    com.gamestore.app.models.OrderStatus.PENDING -> "В обработке"
                    com.gamestore.app.models.OrderStatus.COMPLETED -> "Завершен"
                    com.gamestore.app.models.OrderStatus.CANCELLED -> "Отменен"
                }

                // Показываем количество игр в заказе
                val itemCount = order.items.sumOf { it.quantity }
                textOrderItems.text = "$itemCount ${getGameWord(itemCount)}"
                
                // Показываем первые несколько игр
                val gameNames = order.items.take(2).joinToString(", ") { it.game.title }
                val additionalCount = order.items.size - 2
                textOrderGames.text = if (additionalCount > 0) {
                    "$gameNames и еще $additionalCount"
                } else {
                    gameNames
                }
            }
        }
        
        private fun getGameWord(count: Int): String {
            return when {
                count % 10 == 1 && count % 100 != 11 -> "игра"
                count % 10 in 2..4 && count % 100 !in 12..14 -> "игры"
                else -> "игр"
            }
        }
    }
    
    private class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
}