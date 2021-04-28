package cloud.robert.mcumovies.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cloud.robert.mcumovies.business.repositories.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DownloadDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted parameters: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        try {
            repository.fetchData()
        } catch (error: Exception) {
            return Result.failure()
        }

        return Result.success()
    }

}