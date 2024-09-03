package com.example.compra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class PedidoRealizado : AppCompatActivity() {

    private lateinit var tvTotalConfirmation: TextView
    private lateinit var tvPaymentMethodConfirmation: TextView
    private lateinit var tvDateConfirmation: TextView
    private lateinit var tvTimeConfirmation: TextView
    private lateinit var tvCodeConfirmation: TextView
    private lateinit var btnContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_realizado)

        tvTotalConfirmation = findViewById(R.id.tvTotalConfirmation)
        tvPaymentMethodConfirmation = findViewById(R.id.tvPaymentMethodConfirmation)
        tvDateConfirmation = findViewById(R.id.tvDateConfirmation)
        tvTimeConfirmation = findViewById(R.id.tvTimeConfirmation)
        tvCodeConfirmation = findViewById(R.id.tvCodeConfirmation)
        btnContinue = findViewById(R.id.btnContinuar)

        val total = intent.getStringExtra("total") ?: "0.0"
        val paymentMethod = intent.getStringExtra("paymentMethod") ?: "No especificado"
        val deliveryAddress = intent.getStringExtra("deliveryAddress") ?: "Dirección no especificada"

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val currentDate = dateFormat.format(Date())
        val currentTime = timeFormat.format(Date())
        val randomCode = generateRandomCode()

        tvTotalConfirmation.text = "Total: $$total"
        tvPaymentMethodConfirmation.text = "Método de pago: $paymentMethod"
        tvDateConfirmation.text = "Fecha: $currentDate"
        tvTimeConfirmation.text = "Hora: $currentTime"
        tvCodeConfirmation.text = "Código: $randomCode"

        btnContinue.setOnClickListener {
            val intent = Intent(this, OrderTrackingActivity::class.java)
            intent.putExtra("total", total)
            intent.putExtra("deliveryAddress", deliveryAddress)  // Asegúrate de enviar la dirección aquí
            intent.putExtra("paymentMethod", paymentMethod)
            startActivity(intent)
        }
    }

    private fun generateRandomCode(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..6)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}