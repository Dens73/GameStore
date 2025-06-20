package com.gamestore.app.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.gamestore.app.adapters.GameAdapter
import com.gamestore.app.data.GameRepository
import com.gamestore.app.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var gameAdapter: GameAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupSearchInput()
        showFeaturedGames()
    }
    
    private fun setupRecyclerView() {
        gameAdapter = GameAdapter { game ->
            val fragment = ProductDetailFragment.newInstance(game.id)
            parentFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit()
        }
        
        binding.recyclerViewSearchResults.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = gameAdapter
        }
    }
    
    private fun setupSearchInput() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                if (query.isEmpty()) {
                    showFeaturedGames()
                } else {
                    searchGames(query)
                }
            }
        })
    }
    
    private fun searchGames(query: String) {
        val results = GameRepository.searchGames(query)
        gameAdapter.submitList(results)
        
        binding.textSearchTitle.text = if (results.isEmpty()) {
            "Ничего не найдено"
        } else {
            "Результаты поиска (${results.size})"
        }
    }
    
    private fun showFeaturedGames() {
        val featuredGames = GameRepository.getFeaturedGames()
        gameAdapter.submitList(featuredGames)
        binding.textSearchTitle.text = "Рекомендуемые игры"
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}