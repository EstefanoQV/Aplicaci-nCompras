package com.example.compra

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Pagar : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var tvSubtotal: TextView
    private lateinit var tvTaxes: TextView
    private lateinit var tvTotal: TextView
    private lateinit var btnAddCard: Button
    private lateinit var etDeliveryAddress: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagar)

        radioGroup = findViewById(R.id.radioGroup)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvTaxes = findViewById(R.id.tvTaxes)
        tvTotal = findViewById(R.id.tvTotal)
        btnAddCard = findViewById(R.id.btnAddCard)
        etDeliveryAddress = findViewById(R.id.etDeliveryAddress)

        val subtotal = intent.getStringExtra("total")
        tvSubtotal.text = "$$subtotal"

        val taxes = subtotal?.toDouble()?.times(0.10) ?: 0.0
        tvTaxes.text = "$$taxes"

        val total = subtotal?.toDouble()?.plus(taxes) ?: 0.0
        tvTotal.text = "$$total"

        btnAddCard.setOnClickListener {
            val intent = Intent(this, AgregarTarjeta::class.java)
            startActivityForResult(intent, ADD_CARD_REQUEST_CODE)
        }

        val btnPay: Button = findViewById(R.id.btnPay)
        btnPay.setOnClickListener {
            val paymentMethod = getSelectedPaymentMethod()
            val deliveryAddress = etDeliveryAddress.text.toString().trim()  // Asegúrate de obtener el texto sin espacios adicionales
            val intent = Intent(this, PedidoRealizado::class.java)
            intent.putExtra("total", total.toString())
            intent.putExtra("paymentMethod", paymentMethod)
            intent.putExtra("deliveryAddress", deliveryAddress)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val cardInfo = data?.getStringExtra("cardInfo")
            val cardImage = data?.getIntExtra("cardImage", R.drawable.imageiconvisa)
            if (cardInfo != null) {
                val rbNewCard = RadioButton(this)
                rbNewCard.id = View.generateViewId()
                rbNewCard.text = cardInfo
                rbNewCard.setCompoundDrawablesWithIntrinsicBounds(cardImage!!, 0, 0, 0)
                radioGroup.addView(rbNewCard, 0)

                btnAddCard.isEnabled = false
            }
        }
    }

    private fun getSelectedPaymentMethod(): String {
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        val selectedRadioButton: RadioButton = findViewById(selectedRadioButtonId)
        return selectedRadioButton.text.toString()
    }

    companion object {
        const val ADD_CARD_REQUEST_CODE = 1
    }
}
