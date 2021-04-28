package cloud.robert.mcumovies.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class McuApp : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i("MCU Movies", "FCM token ${it.result}")
            }
        }

        // Create main channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("main", "Main channel", importance)
            channel.description = "Channel principal"
            NotificationManagerCompat
                .from(this)
                .createNotificationChannel(channel)
        }
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()
}