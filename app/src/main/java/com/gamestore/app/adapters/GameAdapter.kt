package com.gamestore.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gamestore.app.databinding.ItemGameBinding
import com.gamestore.app.models.Game
import com.bumptech.glide.Glide


class GameAdapter(
    private val onGameClick: (Game) -> Unit
) : ListAdapter<Game, GameAdapter.GameViewHolder>(GameDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GameViewHolder(
        private val binding: ItemGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game) {
            binding.apply {
                textGameTitle.text = game.title
                textGameGenre.text = game.genre
                textGamePrice.text = "${game.price.toInt()} ₽"
                ratingBar.rating = game.rating
                textRating.text = game.rating.toString()

                if (game.isOnSale) {
                    textDiscountBadge.visibility = View.VISIBLE
                    textDiscountBadge.text = "-${game.discountPercent}%"

                    if (game.originalPrice != null) {
                        textOriginalPrice.visibility = View.VISIBLE
                        textOriginalPrice.text = "${game.originalPrice.toInt()} ₽"
                    }
                } else {
                    textDiscountBadge.visibility = View.GONE
                    textOriginalPrice.visibility = View.GONE
                }

                // Загрузка изображения с помощью Glide
                Glide.with(binding.root.context)
                    .load(game.image)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(imageGame)

                root.setOnClickListener {
                    onGameClick(game)
                }
            }
        }
    }
    
    private class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }
}