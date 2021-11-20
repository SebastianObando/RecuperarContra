package co.edu.eam.mytestapp.utils

import android.net.ConnectivityManager
import android.net.Network

class EstadoConexion( var resul: (Boolean) -> Unit ): ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        resul(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        resul(false)
    }
}