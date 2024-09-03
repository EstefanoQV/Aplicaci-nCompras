package com.example.mapa

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class OrderTrackingFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var tvAddress: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvPaymentMethod: TextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_tracking, container, false)

        tvAddress = view.findViewById(R.id.tvAddress)
        tvTotal = view.findViewById(R.id.tvTotal)
        tvPaymentMethod = view.findViewById(R.id.tvPaymentMethod)

        val address = arguments?.getString("deliveryAddress") ?: "Dirección no especificada"
        val total = arguments?.getString("total") ?: "0.0"
        val paymentMethod = arguments?.getString("paymentMethod") ?: "No especificado"

        tvAddress.text = "Dirección: $address"
        tvTotal.text = "Total: $$total"
        tvPaymentMethod.text = "Método de pago: $paymentMethod"

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(), R.raw.map_style
                )
            )
            if (!success) {
                Log.e("OrderTrackingFragment", "Fallo al cargar el estilo del mapa.")
            }
        } catch (e: Exception) {
            Log.e("OrderTrackingFragment", "Error al cargar el estilo del mapa: ${e.message}")
        }

        val vitalFoods = LatLng(-8.123264253942569, -79.03511320903347)

        val bitmapTiendita = BitmapFactory.decodeResource(resources, R.drawable.tiendita)
        val smallMarkerTiendita = Bitmap.createScaledBitmap(bitmapTiendita, 140, 140, false)

        googleMap.addMarker(
            MarkerOptions()
                .position(vitalFoods)
                .title("Vital Foods")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerTiendita))
        )

        val customLocation = LatLng(-8.1410217, -79.0470593)

        val bitmapCasita = BitmapFactory.decodeResource(resources, R.drawable.casita)
        val smallMarkerCasita = Bitmap.createScaledBitmap(bitmapCasita, 130, 140, false)

        googleMap.addMarker(
            MarkerOptions()
                .position(customLocation)
                .title("Casa")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerCasita))
        )

        val deli = LatLng(-8.136375735593223, -79.0373849245103)

        val bitmapdeli = BitmapFactory.decodeResource(resources, R.drawable.deli)
        val smallMarkerDeli = Bitmap.createScaledBitmap(bitmapdeli, 100, 100, false)

        googleMap.addMarker(
            MarkerOptions()
                .position(deli)
                .title("Repartidor")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerDeli))
        )

        val builder = LatLngBounds.Builder()
        builder.include(vitalFoods)
        builder.include(customLocation)
        val bounds = builder.build()

        val padding = 310
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.moveCamera(cameraUpdate)

        val rightOffset = 10
        val upOffset = -215
        googleMap.animateCamera(CameraUpdateFactory.scrollBy(rightOffset.toFloat(), upOffset.toFloat()))
    }

    override fun onResume() {
        super.onResume()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.onResume()
    }

    override fun onPause() {
        super.onPause()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.onLowMemory()
    }
}
