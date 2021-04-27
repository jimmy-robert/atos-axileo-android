package cloud.robert.mcumovies.services

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import cloud.robert.mcumovies.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class McuMessagingService : FirebaseMessagingService() {
    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)

        // todo: save token to target specific device later
        Log.i("MCU Movies", "FCM new token: $newToken")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val movieId = message.data["movieId"]?.toInt() ?: 0

        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.movieFragment)
            .setArguments(bundleOf("movieId" to movieId))
            .createPendingIntent()

        val notification = NotificationCompat.Builder(this, "main")
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat
            .from(this)
            .notify(movieId, notification)
    }
}