package cloud.robert.mcumovies.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cloud.robert.mcumovies.business.repositories.Repository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class DownloadDataWorker(
    context: Context,
    parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WorkerEntryPoint {
        fun provideRepository(): Repository
    }

    override suspend fun doWork(): Result {
        val entryPoint = EntryPointAccessors.fromApplication(applicationContext, WorkerEntryPoint::class.java)
        val repository = entryPoint.provideRepository()

        try {
            repository.fetchData()
        } catch (error: Exception) {
            return Result.failure()
        }

        return Result.success()
    }

}