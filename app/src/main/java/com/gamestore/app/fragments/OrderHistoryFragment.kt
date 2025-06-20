package com.gamestore.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gamestore.app.adapters.OrderAdapter
import com.gamestore.app.databinding.FragmentOrderHistoryBinding
import com.gamestore.app.utils.AuthManager
import com.gamestore.app.utils.OrderManager

class OrderHistoryFragment : Fragment() {
    
    private var _binding: FragmentOrderHistoryBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var orderAdapter: OrderAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupBackButton()
        loadOrders()
    }
    
    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter()
        
        binding.recyclerViewOrders.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
    }
    
    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    
    private fun loadOrders() {
        val currentUser = AuthManager.getCurrentUser()
        if (currentUser != null) {
            val orders = OrderManager.getUserOrders(currentUser.id)
            
            if (orders.isEmpty()) {
                binding.layoutEmptyOrders.visibility = View.VISIBLE
                binding.recyclerViewOrders.visibility = View.GONE
            } else {
                binding.layoutEmptyOrders.visibility = View.GONE
                binding.recyclerViewOrders.visibility = View.VISIBLE
                orderAdapter.submitList(orders)
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}