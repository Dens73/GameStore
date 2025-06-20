package com.gamestore.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.gamestore.app.R
import com.gamestore.app.adapters.GameAdapter
import com.gamestore.app.data.GameRepository
import com.gamestore.app.databinding.FragmentHomeBinding
import com.gamestore.app.models.GameCategory
import com.gamestore.app.utils.AuthManager
import com.gamestore.app.utils.CartManager

class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var cartUpdateListener: (() -> Unit)? = null
    
    private lateinit var gameAdapter: GameAdapter
    private var currentCategory = GameCategory.NEW_RELEASES
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupCategoryTabs()
        setupCartButton()
        setupBalanceDisplay()
        loadGames()
        
        // Обновляем корзину при изменениях
        cartUpdateListener = {
            if (_binding != null) {
                updateCartBadge()
            }
        }
        CartManager.setCartUpdateListener(cartUpdateListener!!)
    }
    
    private fun setupRecyclerView() {
        gameAdapter = GameAdapter { game ->
            // Navigate to game detail
            val fragment = ProductDetailFragment.newInstance(game.id)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        
        binding.recyclerViewGames.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = gameAdapter
        }
    }
    
    private fun setupCategoryTabs() {
        binding.chipNewReleases.setOnClickListener {
            selectCategory(GameCategory.NEW_RELEASES)
        }
        
        binding.chipAction.setOnClickListener {
            selectCategory(GameCategory.ACTION)
        }
        
        binding.chipRpg.setOnClickListener {
            selectCategory(GameCategory.RPG)
        }
        
        binding.chipStrategy.setOnClickListener {
            selectCategory(GameCategory.STRATEGY)
        }
        
        binding.chipIndie.setOnClickListener {
            selectCategory(GameCategory.INDIE)
        }
        
        binding.chipSale.setOnClickListener {
            selectCategory(GameCategory.SALE)
        }
    }
    
    private fun setupCartButton() {
        binding.buttonCart.setOnClickListener {
            // Navigate to cart
            val cartFragment = CartFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, cartFragment)
                .addToBackStack(null)
                .commit()
        }
        updateCartBadge()
    }
    
    private fun setupBalanceDisplay() {
        updateBalanceDisplay()
    }
    
    private fun updateBalanceDisplay() {
        val user = AuthManager.getCurrentUser()
        if (user != null) {
            binding.textBalance.text = "${user.balance.toInt()} ₽"
            binding.layoutBalance.visibility = View.VISIBLE
        } else {
            binding.layoutBalance.visibility = View.GONE
        }
    }

    private fun updateCartBadge() {
        _binding?.let { binding ->
            val itemCount = CartManager.getCartItemCount()
            if (itemCount > 0) {
                binding.textCartBadge.text = itemCount.toString()
                binding.textCartBadge.visibility = View.VISIBLE
            } else {
                binding.textCartBadge.visibility = View.GONE
            }
        }
    }
    
    private fun selectCategory(category: GameCategory) {
        currentCategory = category
        
        // Reset all chips
        binding.chipNewReleases.isChecked = false
        binding.chipAction.isChecked = false
        binding.chipRpg.isChecked = false
        binding.chipStrategy.isChecked = false
        binding.chipIndie.isChecked = false
        binding.chipSale.isChecked = false
        
        // Select current chip
        when (category) {
            GameCategory.NEW_RELEASES -> binding.chipNewReleases.isChecked = true
            GameCategory.ACTION -> binding.chipAction.isChecked = true
            GameCategory.RPG -> binding.chipRpg.isChecked = true
            GameCategory.STRATEGY -> binding.chipStrategy.isChecked = true
            GameCategory.INDIE -> binding.chipIndie.isChecked = true
            GameCategory.SALE -> binding.chipSale.isChecked = true
        }
        
        loadGames()
    }
    
    private fun loadGames() {
        val games = if (currentCategory == GameCategory.SALE) {
            GameRepository.getSaleGames()
        } else {
            GameRepository.getGamesByCategory(currentCategory).ifEmpty {
                GameRepository.getFeaturedGames()
            }
        }
        gameAdapter.submitList(games)
    }
    
    override fun onResume() {
        super.onResume()
        updateCartBadge()
        updateBalanceDisplay()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cartUpdateListener = null
        _binding = null
    }
}