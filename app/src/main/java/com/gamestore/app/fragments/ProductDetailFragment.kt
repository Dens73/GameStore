package com.gamestore.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gamestore.app.data.GameRepository
import com.gamestore.app.databinding.FragmentProductDetailBinding
import com.gamestore.app.models.CartItem
import com.gamestore.app.models.Platform
import com.gamestore.app.utils.CartManager
import com.bumptech.glide.Glide

class ProductDetailFragment : Fragment() {
    
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    
    private var gameId: Int = -1
    private var selectedPlatform: Platform = Platform.PC
    
    companion object {
        private const val ARG_GAME_ID = "game_id"
        
        fun newInstance(gameId: Int): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val args = Bundle()
            args.putInt(ARG_GAME_ID, gameId)
            fragment.arguments = args
            return fragment
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameId = it.getInt(ARG_GAME_ID)
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val game = GameRepository.getGameById(gameId)
        if (game == null) {
            parentFragmentManager.popBackStack()
            return
        }
        
        setupUI(game)
        setupPlatformSelection(game)
        setupAddToCartButton(game)
    }
    
    private fun setupUI(game: com.gamestore.app.models.Game) {
        binding.apply {
            textGameTitle.text = game.title
            textGameDescription.text = game.description
            textGameGenre.text = game.genre
            textGameDeveloper.text = "Разработчик: ${game.developer}"
            textGameReleaseDate.text = "Дата выхода: ${game.releaseDate}"
            ratingBar.rating = game.rating
            textRating.text = game.rating.toString()
            
            if (game.isOnSale && game.originalPrice != null) {
                textOriginalPrice.text = "${game.originalPrice.toInt()} ₽"
                textOriginalPrice.visibility = View.VISIBLE
                textDiscountPercent.text = "-${game.discountPercent}%"
                textDiscountPercent.visibility = View.VISIBLE
            } else {
                textOriginalPrice.visibility = View.GONE
                textDiscountPercent.visibility = View.GONE
            }
            
            textGamePrice.text = "${game.price.toInt()} ₽"


            Glide.with(requireContext())
                .load(game.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imageGame)

        }
    }
    
    private fun setupPlatformSelection(game: com.gamestore.app.models.Game) {
        binding.chipGroupPlatforms.removeAllViews()
        
        game.platform.forEach { platform ->
            val chip = com.google.android.material.chip.Chip(requireContext())
            chip.text = when (platform) {
                Platform.PC -> "PC"
                Platform.PLAYSTATION -> "PlayStation"
                Platform.XBOX -> "Xbox"
                Platform.NINTENDO_SWITCH -> "Nintendo Switch"
                Platform.MOBILE -> "Mobile"
            }
            chip.isCheckable = true
            chip.setOnClickListener {
                selectedPlatform = platform
            }
            
            if (platform == selectedPlatform) {
                chip.isChecked = true
            }
            
            binding.chipGroupPlatforms.addView(chip)
        }
    }
    
    private fun setupAddToCartButton(game: com.gamestore.app.models.Game) {
        binding.buttonAddToCart.setOnClickListener {
            val cartItem = CartItem(
                game = game,
                quantity = 1,
                selectedPlatform = selectedPlatform
            )
            
            CartManager.addToCart(cartItem)
            Toast.makeText(requireContext(), "Добавлено в корзину!", Toast.LENGTH_SHORT).show()
        }
        
        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}