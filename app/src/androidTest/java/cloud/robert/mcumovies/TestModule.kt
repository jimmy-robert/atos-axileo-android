package cloud.robert.mcumovies

import android.content.Context
import androidx.room.Room
import cloud.robert.mcumovies.app.DatabaseModule
import cloud.robert.mcumovies.business.databases.database.McuDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : McuDatabase =
        Room.inMemoryDatabaseBuilder(context, McuDatabase::class.java).build()
}