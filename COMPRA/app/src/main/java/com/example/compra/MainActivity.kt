package com.example.compra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var productsLayout: LinearLayout
    private lateinit var viewCartButton: Button

    private val items = arrayOf(
        "GALLETA ANIMALITOS - $10",
        "LECHE DE CABRA JAPONESA - $20",
        "PAN DE MOLDE - $30"
    )
    private val itemPrices = arrayOf(10.0, 20.0, 30.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productsLayout = findViewById(R.id.productsLayout)
        viewCartButton = findViewById(R.id.viewCartButton)

        // Agregar productos dinámicamente a la vista
        for (i in items.indices) {
            val productName = items[i]
            val productPrice = itemPrices[i]

            val productView = layoutInflater.inflate(R.layout.item_product, null)
            val itemNameTextView = productView.findViewById<TextView>(R.id.itemNameTextView)
            val addToCartButton = productView.findViewById<ImageButton>(R.id.addToCartButton)

            itemNameTextView.text = productName
            addToCartButton.setOnClickListener {
                addToCart(productName, productPrice)
            }

            productsLayout.addView(productView)
        }

        viewCartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addToCart(item: String, price: Double) {
        CartManager.cartItems.add(CartItem(item, price))
        // Aquí puedes implementar la lógica para agregar el artículo al carrito
        Toast.makeText(this, "Agregado al carrito: $item", Toast.LENGTH_SHORT).show()
    }

}