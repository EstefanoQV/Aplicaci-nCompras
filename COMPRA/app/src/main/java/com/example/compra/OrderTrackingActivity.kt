package com.example.compra

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLngBounds

class OrderTrackingActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var tvAddress: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvPaymentMethod: TextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_tracking)

        tvAddress = findViewById(R.id.tvAddress)
        tvTotal = findViewById(R.id.tvTotal)
        tvPaymentMethod = findViewById(R.id.tvPaymentMethod)

        val address = intent.getStringExtra("deliveryAddress") ?: "Dirección no especificada"
        val total = intent.getStringExtra("total") ?: "0.0"
        val paymentMethod = intent.getStringExtra("paymentMethod") ?: "No especificado"

        tvAddress.text = "Dirección: $address"
        tvTotal.text = "Total: $$total"
        tvPaymentMethod.text = "Método de pago: $paymentMethod"

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        try {
            // Cargar el estilo del mapa desde el archivo JSON
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.map_style
                )
            )
            if (!success) {
                Log.e("MainActivity", "Fallo al cargar el estilo del mapa.")
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al cargar el estilo del mapa: ${e.message}")
        }

        val vitalFoods = LatLng(-8.123264253942569, -79.03511320903347)

        // Redimensionar la imagen del marcador de tienda
        val bitmapTiendita = BitmapFactory.decodeResource(resources, R.drawable.tiendita)
        val smallMarkerTiendita = Bitmap.createScaledBitmap(bitmapTiendita, 140, 140, false)

        // Agregar marcador de la tienda
        googleMap.addMarker(
            MarkerOptions()
                .position(vitalFoods)
                .title("Vital Foods")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerTiendita))
        )

        // Coordenadas de la ubicación personalizada (ejemplo: Lima, Perú)
        val customLocation = LatLng(-8.1410217, -79.0470593)

        // Redimensionar la imagen del marcador de la ubicación personalizada (casita)
        val bitmapCasita = BitmapFactory.decodeResource(resources, R.drawable.casita)
        val smallMarkerCasita = Bitmap.createScaledBitmap(bitmapCasita, 140, 140, false)

        // Agregar marcador en la ubicación personalizada con icono de casita
        googleMap.addMarker(
            MarkerOptions()
                .position(customLocation)
                .title("Casa")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerCasita))
        )

        // Crear un límite que contenga ambos marcadores
        val builder = LatLngBounds.Builder()
        builder.include(vitalFoods)
        builder.include(customLocation)
        val bounds = builder.build()

        // Mover la cámara del mapa para que muestre ambos marcadores y ajustar el zoom
        val padding = 100 // Padding en píxeles desde los bordes del mapa
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.moveCamera(cameraUpdate)
    }
}
