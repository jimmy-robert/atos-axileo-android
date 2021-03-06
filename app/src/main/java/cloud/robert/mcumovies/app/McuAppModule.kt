package cloud.robert.mcumovies.app

import android.content.Context
import android.hardware.SensorManager
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import cloud.robert.mcumovies.business.databases.database.McuDatabase
import cloud.robert.mcumovies.business.network.McuApi
import cloud.robert.mcumovies.business.preferences.Preferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object McuAppModule {

    @Provides
    @Singleton
    fun provideApi(): McuApi = Retrofit.Builder()
        .baseUrl("https://jsonstorage.net")
        .client(
            OkHttpClient.Builder()
                //.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(McuApi::class.java)

    @Provides
    fun provideMasterKey(@ApplicationContext context: Context): MasterKey =
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    @Provides
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context,
        masterKey: MasterKey
    ): Preferences {
        return Preferences(
            context.getSharedPreferences("clear-data", Context.MODE_PRIVATE),
            EncryptedSharedPreferences.create(
                context,
                "secured-data",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        )
    }

    @Provides
    fun provideLocationClient(@ApplicationContext context: Context) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideSensorManager(@ApplicationContext context: Context) : SensorManager {
        return context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): McuDatabase = Room
        .databaseBuilder(context, McuDatabase::class.java, "mcu-db")
        .build()
}