package com.gamestore.app.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gamestore.app.R
import com.gamestore.app.adapters.CartAdapter
import com.gamestore.app.databinding.FragmentCartBinding
import com.gamestore.app.models.TransactionType
import com.gamestore.app.utils.AuthManager
import com.gamestore.app.utils.BalanceManager
import com.gamestore.app.utils.CartManager
import com.gamestore.app.utils.OrderManager

class CartFragment : Fragment() {
    
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var cartAdapter: CartAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupCheckoutButton()
        setupBackButton()
        updateCartUI()
    }
    
    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onQuantityChanged = { cartItem, newQuantity ->
                CartManager.updateQuantity(cartItem.game.id, newQuantity)
                updateCartUI()
            },
            onRemoveItem = { cartItem ->
                CartManager.removeFromCart(cartItem.game.id)
                updateCartUI()
            }
        )
        
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }
    
    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    
    private fun setupCheckoutButton() {
        binding.buttonCheckout.setOnClickListener {
            val cartItems = CartManager.getCartItems()
            if (cartItems.isEmpty()) {
                Toast.makeText(requireContext(), "Корзина пуста", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val currentUser = AuthManager.getCurrentUser()
            if (currentUser == null) {
                Toast.makeText(requireContext(), "Необходимо войти в аккаунт", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val total = CartManager.getSubtotal()
            if (currentUser.balance < total) {
                showInsufficientBalanceDialog(total - currentUser.balance)
                return@setOnClickListener
            }
            
            showCheckoutDialog(total)
        }
    }
    
    private fun showCheckoutDialog(total: Double) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_checkout, null)
        val editEmail = dialogView.findViewById<EditText>(R.id.edit_email)
        val editPhone = dialogView.findViewById<EditText>(R.id.edit_phone)
        
        // Предзаполняем email текущего пользователя
        AuthManager.getCurrentUser()?.let { user ->
            editEmail.setText(user.email)
        }
        
        AlertDialog.Builder(requireContext())
            .setTitle("Оформление заказа")
            .setView(dialogView)
            .setPositiveButton("Оплатить ${total.toInt()} ₽") { _, _ ->
                val email = editEmail.text.toString().trim()
                val phone = editPhone.text.toString().trim()
                
                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(requireContext(), "Введите корректный email", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                
                if (phone.isEmpty() || phone.length < 10) {
                    Toast.makeText(requireContext(), "Введите корректный номер телефона", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                
                processOrder(total)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    
    private fun processOrder(total: Double) {
        val currentUser = AuthManager.getCurrentUser() ?: return
        val cartItems = CartManager.getCartItems()
        
        // Списываем средства
        if (AuthManager.deductBalance(total)) {
            // Создаем заказ
            val order = OrderManager.createOrder(currentUser.id, cartItems, total)
            
            // Добавляем транзакцию
            BalanceManager.addTransaction(
                amount = total,
                type = TransactionType.PURCHASE,
                description = "Покупка игр (заказ ${order.id})"
            )
            
            // Очищаем корзину
            CartManager.clearCart()
            
            Toast.makeText(
                requireContext(), 
                "Заказ успешно оформлен!\nНомер заказа: ${order.id}", 
                Toast.LENGTH_LONG
            ).show()
            
            updateCartUI()
        } else {
            Toast.makeText(requireContext(), "Недостаточно средств на балансе", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showInsufficientBalanceDialog(missingAmount: Double) {
        AlertDialog.Builder(requireContext())
            .setTitle("Недостаточно средств")
            .setMessage("На вашем балансе недостаточно средств для покупки.\nНе хватает: ${missingAmount.toInt()} ₽\n\nХотите пополнить баланс?")
            .setPositiveButton("Пополнить") { _, _ ->
                showTopUpDialog()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    
    private fun showTopUpDialog() {
        val editText = EditText(requireContext()).apply {
            hint = "Сумма пополнения"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }
        
        AlertDialog.Builder(requireContext())
            .setTitle("Пополнение баланса")
            .setView(editText)
            .setPositiveButton("Пополнить") { _, _ ->
                val amountStr = editText.text.toString()
                if (amountStr.isNotEmpty()) {
                    val amount = amountStr.toDoubleOrNull()
                    if (amount != null && amount > 0) {
                        AuthManager.addBalance(amount)
                        Toast.makeText(
                            requireContext(), 
                            "Баланс пополнен на ${amount.toInt()} ₽", 
                            Toast.LENGTH_SHORT
                        ).show()
                        updateCartUI()
                    } else {
                        Toast.makeText(requireContext(), "Введите корректную сумму", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    
    private fun updateCartUI() {
        val cartItems = CartManager.getCartItems()
        val currentUser = AuthManager.getCurrentUser()
        
        if (cartItems.isEmpty()) {
            binding.layoutEmptyCart.visibility = View.VISIBLE
            binding.layoutCartContent.visibility = View.GONE
        } else {
            binding.layoutEmptyCart.visibility = View.GONE
            binding.layoutCartContent.visibility = View.VISIBLE
            
            cartAdapter.submitList(cartItems)
            
            val total = CartManager.getSubtotal()
            binding.textTotal.text = "${total.toInt()} ₽"
            
            // Показываем баланс пользователя
            if (currentUser != null) {
                binding.textUserBalance.text = "Ваш баланс: ${currentUser.balance.toInt()} ₽"
                binding.textUserBalance.visibility = View.VISIBLE
                
                // Меняем цвет кнопки в зависимости от достаточности средств
                if (currentUser.balance >= total) {
                    binding.buttonCheckout.backgroundTintList = 
                        requireContext().getColorStateList(R.color.accent_primary)
                    binding.buttonCheckout.text = "Оплатить ${total.toInt()} ₽"
                } else {
                    binding.buttonCheckout.backgroundTintList = 
                        requireContext().getColorStateList(R.color.error)
                    binding.buttonCheckout.text = "Недостаточно средств"
                }
            } else {
                binding.textUserBalance.visibility = View.GONE
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}