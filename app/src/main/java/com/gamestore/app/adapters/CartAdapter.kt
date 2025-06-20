package com.gamestore.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gamestore.app.databinding.ItemCartBinding
import com.gamestore.app.models.CartItem
import com.bumptech.glide.Glide


class CartAdapter(
    private val onQuantityChanged: (CartItem, Int) -> Unit,
    private val onRemoveItem: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            binding.apply {
                textGameTitle.text = cartItem.game.title
                textGamePlatform.text = when (cartItem.selectedPlatform) {
                    com.gamestore.app.models.Platform.PC -> "PC"
                    com.gamestore.app.models.Platform.PLAYSTATION -> "PlayStation"
                    com.gamestore.app.models.Platform.XBOX -> "Xbox"
                    com.gamestore.app.models.Platform.NINTENDO_SWITCH -> "Nintendo Switch"
                    com.gamestore.app.models.Platform.MOBILE -> "Mobile"
                }
                textGamePrice.text = "${cartItem.game.price.toInt()} ₽"
                textQuantity.text = cartItem.quantity.toString()

                val totalPrice = cartItem.game.price * cartItem.quantity
                textTotalPrice.text = "${totalPrice.toInt()} ₽"

                // Загрузка изображения с помощью Glide
                Glide.with(binding.root.context)
                    .load(cartItem.game.image)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(imageGame)

                buttonIncrease.setOnClickListener {
                    onQuantityChanged(cartItem, cartItem.quantity + 1)
                }

                buttonDecrease.setOnClickListener {
                    if (cartItem.quantity > 1) {
                        onQuantityChanged(cartItem, cartItem.quantity - 1)
                    }
                }

                buttonRemove.setOnClickListener {
                    onRemoveItem(cartItem)
                }
            }
        }
    }


    private class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.game.id == newItem.game.id && 
                   oldItem.selectedPlatform == newItem.selectedPlatform
        }
        
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}