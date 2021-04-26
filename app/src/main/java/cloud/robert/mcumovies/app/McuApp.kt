package cloud.robert.mcumovies.app

import android.app.Application
import androidx.room.Room
import cloud.robert.mcumovies.business.databases.database.McuDatabase
import cloud.robert.mcumovies.business.network.McuApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class McuApp : Application() {

    companion object {
        private lateinit var instance: McuApp

        val api: McuApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://jsonstorage.net")
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(McuApi::class.java)
        }

        val db: McuDatabase by lazy {
            Room.databaseBuilder(instance, McuDatabase::class.java, "mcu-db").build()
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}