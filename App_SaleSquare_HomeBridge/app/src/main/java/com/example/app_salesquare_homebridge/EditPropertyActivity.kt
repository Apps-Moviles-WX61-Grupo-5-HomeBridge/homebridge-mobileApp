package com.example.app_salesquare_homebridge

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_salesquare_homebridge.models.Location
import com.example.app_salesquare_homebridge.shared.user.UserWrapper
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
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditPropertyActivity : AppCompatActivity(), OnMapReadyCallback {
    private val LOCATION_REQUEST_CODE = 1

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var etAddress: TextInputEditText
    private lateinit var btnVenta : Button
    private lateinit var btnAlquiler : Button
    private var selectedOperation = 0
    private lateinit var btnCasa : Button
    private lateinit var btnDepartamento : Button
    private lateinit var btnTerreno : Button
    private var selectedType = 0
    private lateinit var btnDMas : Button
    private lateinit var btnDMenos : Button
    private lateinit var tvDormitorios : TextView
    private var dormitorios = 0
    private lateinit var btnBMas : Button
    private lateinit var btnBMenos : Button
    private lateinit var tvBanos : TextView
    private var banos = 0
    private lateinit var btnCMas : Button
    private lateinit var btnCMenos : Button
    private lateinit var tvCocheras : TextView
    private var cocheras = 0
    private lateinit var etAreaTechada: TextInputEditText
    private lateinit var etAreaTotal: TextInputEditText
    private lateinit var rgAntiguedad: RadioGroup
    private var antiguedad = 0
    private lateinit var etPrecio : TextInputEditText
    private lateinit var etTitulo : TextInputEditText
    private lateinit var etDescripcion : TextInputEditText
    lateinit var location: Location
    private lateinit var d_UserWrapper: UserWrapper

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.edit_property)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_property)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        mapView = findViewById(R.id.mvMap3)
        etAddress = findViewById(R.id.etAddress)
        btnVenta = findViewById(R.id.btTOV)
        btnAlquiler = findViewById(R.id.btTOA)
        btnCasa = findViewById(R.id.btTIC)
        btnDepartamento = findViewById(R.id.btTID)
        btnTerreno = findViewById(R.id.btTIT)
        btnDMas = findViewById(R.id.btDMas)
        btnDMenos = findViewById(R.id.btDMenos)
        tvDormitorios = findViewById(R.id.tvDormitorios)
        btnBMas = findViewById(R.id.btBMas)
        btnBMenos = findViewById(R.id.btBMenos)
        tvBanos = findViewById(R.id.tvBaños)
        btnCMas = findViewById(R.id.btEMas)
        btnCMenos = findViewById(R.id.btEMenos)
        tvCocheras = findViewById(R.id.tvEstacionamientos)
        etAreaTechada = findViewById(R.id.etAreaTechada)
        etAreaTotal = findViewById(R.id.etAreaTotal)
        rgAntiguedad = findViewById(R.id.rgAntiguedad)
        etPrecio = findViewById(R.id.etPrecio)
        etTitulo = findViewById(R.id.etTitulo)
        etDescripcion = findViewById(R.id.etDescripcion)

        btnVenta.setOnClickListener {
            selectedOperation = 0
            btnVenta.setTextColor(Color.parseColor("#FFD700"))
            btnAlquiler.setTextColor(Color.WHITE)
        }

        btnAlquiler.setOnClickListener {
            selectedOperation = 1
            btnAlquiler.setTextColor(Color.parseColor("#FFD700"))
            btnVenta.setTextColor(Color.WHITE)
        }

        btnCasa.setOnClickListener {
            selectedType = 0
            btnCasa.setTextColor(Color.parseColor("#FFD700"))
            btnDepartamento.setTextColor(Color.WHITE)
            btnTerreno.setTextColor(Color.WHITE)
        }

        btnDepartamento.setOnClickListener {
            selectedType = 1
            btnDepartamento.setTextColor(Color.parseColor("#FFD700"))
            btnCasa.setTextColor(Color.WHITE)
            btnTerreno.setTextColor(Color.WHITE)
        }

        btnTerreno.setOnClickListener {
            selectedType = 2
            btnTerreno.setTextColor(Color.parseColor("#FFD700"))
            btnCasa.setTextColor(Color.WHITE)
            btnDepartamento.setTextColor(Color.WHITE)
        }

        btnDMas.setOnClickListener {
            dormitorios++
            updatenumber()
        }

        btnDMenos.setOnClickListener {
            if (dormitorios > 0) {
                dormitorios--
                updatenumber()
            }
        }

        btnBMas.setOnClickListener {
            banos++
            updatenumber()
        }

        btnBMenos.setOnClickListener {
            if (banos > 0) {
                banos--
                updatenumber()
            }
        }

        btnCMas.setOnClickListener {
            cocheras++
            updatenumber()
        }

        btnCMenos.setOnClickListener {
            if (cocheras > 0) {
                cocheras--
                updatenumber()
            }
        }

        rgAntiguedad.setOnCheckedChangeListener{ _, checkedId ->
            antiguedad = when(checkedId){
                R.id.radio_new -> 0
                R.id.radio_old -> 1
                R.id.radio_construction -> 2
                else -> 0
            }
        }

        d_UserWrapper = intent.getParcelableExtra<UserWrapper>("userWrapper")
            ?: throw IllegalArgumentException("UserWrapper no fue proporcionado")


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermission()
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        setupAddressInputListeners()
        updatenumber()

        this.changeToPhotos()
    }

    private fun changeToPhotos(): Unit {
        val btnCreatePost = findViewById<Button>(R.id.btnContinue)
        btnCreatePost.setOnClickListener {
            saveLocation()
            saveEverything()
        }
    }

    private fun updatenumber(){
        tvDormitorios.text = dormitorios.toString()
        tvBanos.text = banos.toString()
        tvCocheras.text = cocheras.toString()
    }

    private fun saveEverything() {
        val client = OkHttpClient()

        val AreaTotal = etAreaTotal.text.toString().toIntOrNull() ?: 0
        val Precio = etPrecio.text.toString().toIntOrNull() ?: 0
        val Titulo = etTitulo.text.toString()
        val Descripcion = etDescripcion.text.toString()
        val token = d_UserWrapper.token()
        val userId = d_UserWrapper.userId()
        val propertyId = intent.getIntExtra("post_id", -1)
        val userWrapper: UserWrapper? = intent.getParcelableExtra("userWrapper")


        if (propertyId == -1) {
            Toast.makeText(this, "Error: Property ID not provided", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("SaveEverything", "Token: $token")
        Log.d("SaveEverything", "Datos obtenidos - Titulo: $Titulo, Descripcion: $Descripcion, Precio: $Precio, AreaTotal: $AreaTotal")

        val jsonObject = JSONObject().apply {
            put("publicationId", propertyId)
            put("title", Titulo)
            put("description", Descripcion)
            put("price", Precio)
            put("location", location.address) //Location pero solo el address
            put("userId", userId)
            put("propertyType", selectedType) //No puede ser int
            put("operationType", selectedOperation) //No puede ser int
            put("bathrooms", banos)
            put("antiqueType", antiguedad) //no puede ser int
            put("size", AreaTotal)
            put("rooms", dormitorios)
            put("garages", cocheras)
        }

        Log.d("SaveEverything", "JSON Object creado: $jsonObject")

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())
        Log.d("SaveEverything", "Request Body creado")

        val request = okhttp3.Request.Builder()
            .url("https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net/api/v1/publication/updatePublication")
            .put(requestBody)
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .build()

        Log.d("SaveEverything", "Request construido, enviando...")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.d("SaveEverything", "Error en conexión: ${e.message}")
                runOnUiThread {
                    Toast.makeText(
                        this@EditPropertyActivity,
                        "Error de conexión ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val responseBody = response.body?.string()
                Log.d("SaveEverything", "Respuesta recibida: ${responseBody ?: "Sin respuesta"}")

                runOnUiThread {
                    if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                        Toast.makeText(
                            this@EditPropertyActivity,
                            "Propiedad actualizada",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@EditPropertyActivity, PostResultsActivity::class.java)
                        intent.putExtra("userWrapper", d_UserWrapper)
                        //intent.putExtra("publicationId", propertyId)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@EditPropertyActivity,
                            "Error al actualizar la propiedad",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun saveLocation() {
        val address = etAddress.text.toString()
        val latitude = googleMap.cameraPosition.target.latitude
        val longitude = googleMap.cameraPosition.target.longitude

        location = Location(null, address, latitude, longitude)
        // Guardar la instancia de Location en la base de datos
        Toast.makeText(this, "Ubicación guardada", Toast.LENGTH_SHORT).show()
        /*val intent = Intent(this, NewPropertyActivity::class.java)
        val gson = Gson()
        intent.putExtra("userWrapper", d_UserWrapper)
        intent.putExtra("location", gson.toJson(location))
        startActivity(intent)*/

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
    }

    private fun setupAddressInputListeners() {
        etAddress.setOnEditorActionListener { _, _, _ ->
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
                val fullAddress = listOfNotNull(
                    address.thoroughfare,
                    address.subThoroughfare,
                    address.locality,
                    address.adminArea,
                ).joinToString(", ")
                etAddress.setText(fullAddress)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "No se pudo encontrar la ubicación", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateMapFromAddress() {
        val address = etAddress.text.toString()
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocationName(address, 1)
            if (!addresses.isNullOrEmpty()) {
                val location: Address = addresses[0]
                val latLng = LatLng(location.latitude, location.longitude)
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Unable to find location", Toast.LENGTH_SHORT).show()
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