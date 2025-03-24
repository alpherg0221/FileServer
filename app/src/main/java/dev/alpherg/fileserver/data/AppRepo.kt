package dev.alpherg.fileserver.data

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.net.Inet4Address
import javax.inject.Inject

class AppRepo @Inject constructor(@ApplicationContext private val context: Context) {
    fun observeDeviceIP(): Flow<String> {
        val manager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onLinkPropertiesChanged(network: Network, properties: LinkProperties) {
                    super.onLinkPropertiesChanged(network, properties)
                    trySend(properties.linkAddresses.filter { it.address is Inet4Address }[0].address.hostName)
                }
            }
            manager.registerDefaultNetworkCallback(callback)
            awaitClose {
                manager.unregisterNetworkCallback(callback)
            }
        }
    }
}