package com.example.app_salesquare_homebridge.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_salesquare_homebridge.EditImageActivity
import com.example.app_salesquare_homebridge.R
import com.example.app_salesquare_homebridge.models.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import java.io.IOException
import java.util.*

class EditLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private val LOCATION_REQUEST_CODE = 1

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var etAddress: TextInputEditText
    private lateinit var tvCity: AutoCompleteTextView
    private lateinit var tvProvince: AutoCompleteTextView
    private lateinit var tvDistrict: AutoCompleteTextView

    lateinit var location: Location

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_location)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        mapView = findViewById(R.id.mvMap2)
        etAddress = findViewById(R.id.etAddress)
        tvCity = findViewById(R.id.tvCity3)
        tvProvince = findViewById(R.id.tvProvince2)
        tvDistrict = findViewById(R.id.tvDistrict2)
        val btConfirm = findViewById<Button>(R.id.continue_button)
        val ibBack = findViewById<ImageButton>(R.id.ibBack)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermission()
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        setupAddressInputListeners()
        btConfirm.setOnClickListener {
            saveLocation()
        }

        ibBack.setOnClickListener {
            finish()
        }


    }

    private fun loadLocation() {
        val gson = Gson()
        val json = intent.getStringExtra("location")
        location = gson.fromJson(json, Location::class.java) ?: Location(null, "", "", "", "",  0.0, 0.0)

        etAddress.setText(location.address)
        tvCity.setText(location.city)
        tvProvince.setText(location.province)
        tvDistrict.setText(location.district)

        val latLng = LatLng(location.latitude!!, location.longitude!!)
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(latLng).title("Ubicación Seleccionada"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    private fun saveLocation() {
        val address = etAddress.text.toString()
        val city = tvCity.text.toString()
        val province = tvProvince.text.toString()
        val district = tvDistrict.text.toString()
        val latitude = googleMap.cameraPosition.target.latitude
        val longitude = googleMap.cameraPosition.target.longitude

        location = Location(null, address, city, province, district, latitude, longitude)
        // Guardar la instancia de Location en la base de datos
        Toast.makeText(this, "Ubicación guardada", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, EditImageActivity::class.java) //TODO: Cambiar el main activity
        val gson = Gson()
        intent.putExtra("location", gson.toJson(location))
        startActivity(intent)

    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation()
                } else {
                    // Permiso denegado, maneja el caso
                }
            }
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
                    googleMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location"))
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isZoomControlsEnabled = true

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation()
        } else {
            requestLocationPermission()
        }

        googleMap.setOnMapClickListener { latLng ->
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(latLng).title("Ubicación Seleccionada"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            updateAddressFields(latLng)
        }
        loadLocation()
    }

    private fun setupAddressInputListeners() {
        etAddress.setOnEditorActionListener { _, _, _ ->
            updateMapFromAddress()
            true
        }
        tvCity.setOnEditorActionListener { _, _, _ ->
            updateMapFromAddress()
            true
        }
        tvProvince.setOnEditorActionListener { _, _, _ ->
            updateMapFromAddress()
            true
        }
        tvDistrict.setOnEditorActionListener { _, _, _ ->
            updateMapFromAddress()
            true
        }
    }

    private fun updateAddressFields(latLng: LatLng) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address: Address = addresses[0]
                etAddress.setText(address.getAddressLine(0))
                tvCity.setText(address.locality)
                tvProvince.setText(address.adminArea)
                tvDistrict.setText(address.subAdminArea)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "No se pudo encontrar la ubicación", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateMapFromAddress() {
        val address = "${etAddress.text}, ${tvCity.text}, ${tvProvince.text}, ${tvDistrict.text}"
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocationName(address, 1)
            if (!addresses.isNullOrEmpty()) {
                val location: Address = addresses[0]
                val latLng = LatLng(location.latitude, location.longitude)
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(latLng).title("Ubicación Seleccionada"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "No se pudo encontrar la ubicación", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        mapView.onSaveInstanceState(outState)
    }
}