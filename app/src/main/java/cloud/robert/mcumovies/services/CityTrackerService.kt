package cloud.robert.mcumovies.services

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.location.Geocoder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.utils.extensions.locationUpdates
import cloud.robert.mcumovies.views.activities.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CityTrackerService : LifecycleService() {

    @Inject
    lateinit var locationClient: FusedLocationProviderClient

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val notification = createNotification("Waiting for location updates...")
        startForeground(1234, notification)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        locationClient.locationUpdates().observe(this) {
            Geocoder(this, Locale.getDefault()).getFromLocation(it.latitude, it.longitude, 10)
                .firstOrNull { address -> address.locality != null }
                ?.let { address ->
                    val notification =
                        createNotification("${address.locality}, ${address.countryName}")
                    NotificationManagerCompat.from(this).notify(1234, notification)
                }
        }
    }

    private fun createNotification(content: String): Notification {
        val activityIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0)

        return NotificationCompat.Builder(this, "main")
            .setContentTitle("City Tracker")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_location)
            .setContentIntent(pendingIntent)
            .build()
    }
}