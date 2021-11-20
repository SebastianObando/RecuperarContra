package co.edu.eam.mytestapp.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import co.edu.eam.mytestapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.db.EstudianteDbHelper
import co.edu.eam.mytestapp.utils.EstadoConexion
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityMapsBinding
    lateinit var gMap:GoogleMap
    private var tienePermiso = false
    private val defaultLocation = LatLng(4.550923, -75.6557201)
    private var tieneConexion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        pedirPermisoUbicacion()

        comprobarConexion()
    }

    override fun onMapReady(p0: GoogleMap) {
        gMap = p0
        gMap.uiSettings.isZoomControlsEnabled = true

        try {
            if (tienePermiso) {
                gMap.isMyLocationEnabled = true
                gMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                gMap.isMyLocationEnabled = false
                gMap.uiSettings.isMyLocationButtonEnabled = false
            }
        }catch (e:SecurityException){
            e.printStackTrace()
        }

        gMap.addMarker( MarkerOptions().position(defaultLocation).title("Marcador en Armenia") )
        gMap.moveCamera( CameraUpdateFactory.newLatLng(defaultLocation) )

    }

    fun pedirPermisoUbicacion(){
        if( checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ){
            tienePermiso = true
        }else{
            requestPermissions( arrayOf( Manifest.permission.ACCESS_FINE_LOCATION  ), 1 )
        }
    }

    fun comprobarConexion(){
        val connectivityManger = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
            connectivityManger.registerDefaultNetworkCallback(EstadoConexion(::imprimirConexion))
        }else{
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManger.registerNetworkCallback(request, EstadoConexion(::imprimirConexion))
        }
    }

    fun imprimirConexion(valor:Boolean){
        tieneConexion = valor
        Log.e("MapsActivity", "$tieneConexion")
    }


}