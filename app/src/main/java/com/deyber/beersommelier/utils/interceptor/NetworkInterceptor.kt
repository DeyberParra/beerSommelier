package com.deyber.beersommelier.utils.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.deyber.beersommelier.utils.resource.TYPEERROR
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(private val context:Context):Interceptor, ConnectivityManager.NetworkCallback() {

    private var isOnline: Boolean = false
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        if(Build.VERSION.SDK_INT>=29){
            connectivityManager.registerDefaultNetworkCallback(this)
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        if(Build.VERSION.SDK_INT<29){
            isOnline = connectivityManager.activeNetworkInfo?.isConnected?:false
        }

        if(isOnline){
            return chain.proceed(chain.request())
        }else{
            throw IOException(TYPEERROR.NO_NETWORK.toString())
        }
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        isOnline = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}