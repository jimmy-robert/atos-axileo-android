package cloud.robert.mcumovies.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class McuMessagingService : FirebaseMessagingService() {
    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)

        // todo: save token to target specific device later
        Log.i("MCU Movies", "FCM new token: $newToken")
    }
}