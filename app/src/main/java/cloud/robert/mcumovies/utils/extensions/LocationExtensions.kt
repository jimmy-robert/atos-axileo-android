package cloud.robert.mcumovies.utils.extensions

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.tasks.CancellationTokenSource
import kotlin.coroutines.suspendCoroutine

@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.lastKnownLocation(): Location? =
    suspendCoroutine { continuation ->
        lastLocation.addOnCompleteListener { task ->
            continuation.resumeWith(Result.success(task.result))
        }
    }

@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.currentLocation(priority: Int): Location? =
    suspendCoroutine { continuation ->
        getCurrentLocation(
            priority,
            CancellationTokenSource().token
        ).addOnCompleteListener { task ->
            continuation.resumeWith(Result.success(task.result))
        }
    }

fun FusedLocationProviderClient.locationUpdates(): LiveData<Location> = LocationLiveData(this)

private class LocationLiveData(private val locationClient: FusedLocationProviderClient) :
    LiveData<Location>() {

    private var locationCallback: LocationCallback? = null

    override fun onActive() {
        super.onActive()
        registerLocationUpdates()
    }

    override fun onInactive() {
        super.onInactive()
        unregisterLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun registerLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)

                result.lastLocation.let { value = it }
            }
        }.apply {
            locationClient.requestLocationUpdates(
                locationRequest,
                this,
                Looper.getMainLooper()
            )
        }
    }

    private fun unregisterLocationUpdates() {
        locationCallback?.let { locationClient.removeLocationUpdates(it) }
    }
}