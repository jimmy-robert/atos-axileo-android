package cloud.robert.mcumovies.slices

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.SliceAction
import androidx.slice.builders.list
import androidx.slice.builders.row
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.business.databases.database.McuDatabase
import cloud.robert.mcumovies.business.models.relations.ActorWithMovies
import cloud.robert.mcumovies.views.activities.MainActivity
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class McuSliceProvider : SliceProvider() {

    companion object {
        var actorWithMovies: ActorWithMovies? = null
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface McuSliceEntryPoint {
        fun database(): McuDatabase
    }

    lateinit var database: McuDatabase

    override fun onCreateSliceProvider(): Boolean {
        val entryPoint = EntryPointAccessors.fromApplication(
            context!!.applicationContext,
            McuSliceEntryPoint::class.java
        )
        database = entryPoint.database()
        return true
    }

    override fun onBindSlice(sliceUri: Uri?): Slice {
        val activityAction = createActivityAction()

        sliceUri?.path?.removePrefix("/")?.toInt()?.let { actorId ->
            val actorWithMovies = McuSliceProvider.actorWithMovies
            if (actorWithMovies == null) {
                GlobalScope.launch {
                    McuSliceProvider.actorWithMovies = database.actorDao().getAsync(actorId)
                    context?.contentResolver?.notifyChange(
                        "content://cloud.robert.mcumovies/3223".toUri(),
                        null
                    )
                }

                return list(context!!, sliceUri, ListBuilder.INFINITY) {
                    row {
                        primaryAction = activityAction
                        title = "Nothing to show"
                    }
                }
            }

            return list(context!!, sliceUri, ListBuilder.INFINITY) {
                row {
                    primaryAction = activityAction
                    title = actorWithMovies.actor.name
                    subtitle = "${actorWithMovies.movies.size} movies"
                }
            }
        }

        return list(context!!, sliceUri!!, ListBuilder.INFINITY) {
            row {
                primaryAction = activityAction
                title = "Nothing to show"
            }
        }

    }

    private fun createActivityAction(): SliceAction {
        val intent = Intent(context, MainActivity::class.java)
        return SliceAction.create(
            PendingIntent.getActivity(context, 0, intent, 0),
            IconCompat.createWithResource(context, R.drawable.ic_location),
            ListBuilder.ICON_IMAGE,
            "Enter app"
        )
    }
}