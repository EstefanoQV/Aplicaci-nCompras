package com.example.compra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var cartTextView: TextView
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var totalTextView: TextView
    private lateinit var backButton: Button
    private lateinit var payButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartTextView = findViewById(R.id.cartTextView)
        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        totalTextView = findViewById(R.id.totalTextView)
        backButton = findViewById(R.id.backButton)
        payButton = findViewById(R.id.payButton)

        val cartItems = CartManager.cartItems

        val adapter = CartAdapter(cartItems)
        cartRecyclerView.adapter = adapter
        cartRecyclerView.layoutManager = LinearLayoutManager(this)

        updateTotal()

        backButton.setOnClickListener {
            finish()
        }

        payButton.setOnClickListener {
            val subtotal = CartManager.calculateSubtotal()
            val intent = Intent(this, Pagar::class.java)
            intent.putExtra("total", subtotal.toString())
            startActivity(intent)
        }
    }

    private fun updateTotal() {
        var totalPrice = 0.0
        for (item in CartManager.cartItems) {
            totalPrice += item.price
        }
        totalTextView.text = "Total: $$totalPrice"
    }

    override fun onResume() {
        super.onResume()
        updateTotal()
    }
}

