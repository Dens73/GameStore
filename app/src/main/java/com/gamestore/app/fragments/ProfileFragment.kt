package com.gamestore.app.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gamestore.app.R
import com.gamestore.app.databinding.FragmentProfileBinding
import com.gamestore.app.utils.AuthManager

class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        updateUI()
        setupButtons()
    }
    
    private fun updateUI() {
        val currentUser = AuthManager.getCurrentUser()
        
        if (currentUser != null) {
            // Пользователь авторизован
            binding.layoutLoggedIn.visibility = View.VISIBLE
            binding.layoutNotLoggedIn.visibility = View.GONE
            
            binding.textUsername.text = currentUser.username
            binding.textUserId.text = "ID: ${currentUser.id}"
            binding.textEmail.text = currentUser.email
            binding.textRegistrationDate.text = "Дата регистрации: ${currentUser.registrationDate}"
            binding.textBalance.text = "${currentUser.balance.toInt()} ₽"
        } else {
            // Пользователь не авторизован
            binding.layoutLoggedIn.visibility = View.GONE
            binding.layoutNotLoggedIn.visibility = View.VISIBLE
        }
    }
    
    private fun setupButtons() {
        // Кнопка входа
        binding.buttonLogin.setOnClickListener {
            showLoginDialog()
        }
        
        // Кнопка выхода
        binding.buttonLogout.setOnClickListener {
            AuthManager.logout()
            updateUI()
            Toast.makeText(requireContext(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
        }
        
        // Кнопка пополнения баланса
        binding.buttonTopUpBalance.setOnClickListener {
            showTopUpDialog()
        }
        
        // Кнопка истории заказов
        binding.buttonOrderHistory.setOnClickListener {
            val fragment = OrderHistoryFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        
        // Кнопка поддержки
        binding.buttonSupport.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Служба поддержки: support@gamestore.com\nТелефон: +7 (800) 555-GAME",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    
    private fun showLoginDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_login, null)
        val editUsername = dialogView.findViewById<EditText>(R.id.edit_username)
        val editPassword = dialogView.findViewById<EditText>(R.id.edit_password)
        
        AlertDialog.Builder(requireContext())
            .setTitle("Вход в аккаунт")
            .setView(dialogView)
            .setPositiveButton("Войти") { _, _ ->
                val username = editUsername.text.toString().trim()
                val password = editPassword.text.toString().trim()
                
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                
                if (AuthManager.login(username, password)) {
                    updateUI()
                    Toast.makeText(requireContext(), "Добро пожаловать!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    
    private fun showTopUpDialog() {
        val editText = EditText(requireContext()).apply {
            hint = "Сумма пополнения"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }
        
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 20)
            addView(editText)
        }
        
        AlertDialog.Builder(requireContext())
            .setTitle("Пополнение баланса")
            .setView(layout)
            .setPositiveButton("Пополнить") { _, _ ->
                val amountStr = editText.text.toString()
                if (amountStr.isNotEmpty()) {
                    val amount = amountStr.toDoubleOrNull()
                    if (amount != null && amount > 0) {
                        AuthManager.addBalance(amount)
                        updateUI()
                        Toast.makeText(
                            requireContext(), 
                            "Баланс пополнен на ${amount.toInt()} ₽", 
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), "Введите корректную сумму", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    
    override fun onResume() {
        super.onResume()
        updateUI()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}