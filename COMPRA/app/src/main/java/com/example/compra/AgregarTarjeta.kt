package com.example.compra

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgregarTarjeta : AppCompatActivity() {
    private lateinit var btnSave: Button
    private lateinit var etCardNumber: EditText
    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etExpiration: EditText
    private lateinit var etCvv: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_tarjeta)

        btnSave = findViewById(R.id.btnSave)
        etCardNumber = findViewById(R.id.etCardNumber)
        etName = findViewById(R.id.etName)
        etLastName = findViewById(R.id.etLastName)
        etExpiration = findViewById(R.id.etExpiration)
        etCvv = findViewById(R.id.etCvv)

        btnSave.isEnabled = false
        etCardNumber.addTextChangedListener(textWatcher)
        etName.addTextChangedListener(textWatcher)
        etLastName.addTextChangedListener(textWatcher)
        etExpiration.addTextChangedListener(textWatcher)
        etCvv.addTextChangedListener(textWatcher)

        btnSave.setOnClickListener {
            val cardNumber = etCardNumber.text.toString()
            val name = etName.text.toString()
            val lastName = etLastName.text.toString()
            val expiration = etExpiration.text.toString()
            val cvv = etCvv.text.toString()

            if (cardNumber.isNotEmpty() && name.isNotEmpty() && lastName.isNotEmpty() && expiration.isNotEmpty() && cvv.isNotEmpty()) {
                val cardInfo = "Tarjeta: $name $lastName"
                val resultIntent = Intent()
                resultIntent.putExtra("cardInfo", cardInfo)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                Toast.makeText(this, "TARJETA AGREGADA CON ÉXITO", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val cardNumber = etCardNumber.text.toString()
            val name = etName.text.toString()
            val lastName = etLastName.text.toString()
            val expiration = etExpiration.text.toString()
            val cvv = etCvv.text.toString()
            btnSave.isEnabled = cardNumber.isNotEmpty() && name.isNotEmpty() && lastName.isNotEmpty() && expiration.isNotEmpty() && cvv.isNotEmpty()
        }
    }
}


