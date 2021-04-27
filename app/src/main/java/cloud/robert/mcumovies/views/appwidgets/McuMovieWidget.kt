package cloud.robert.mcumovies.views.appwidgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.business.models.entities.Movie
import cloud.robert.mcumovies.business.repositories.Repository
import cloud.robert.mcumovies.utils.extensions.toTmdbImageUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class McuMovieWidget : AppWidgetProvider() {

    @Inject
    lateinit var repository: Repository

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        if (context == null) return
        if (appWidgetManager == null) return
        if (appWidgetIds == null) return

        appWidgetIds.forEach {
            onUpdate(context, appWidgetManager, it)
        }
    }

    private fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.mcu_widget)

        views.setOnClickPendingIntent(
            R.id.widgetRefreshButton,
            createRefreshIntent(context, appWidgetId)
        )

        GlobalScope.launch {
            val movie = repository.getRandomMovie()

            views.setTextViewText(R.id.widgetMovieTitle, movie.title)

            val imageTarget = AppWidgetTarget(context, R.id.widgetMovieBackdrop, views, appWidgetId)
            Glide.with(context)
                .asBitmap()
                .load(movie.backdropPath.toTmdbImageUrl())
                .into(imageTarget)

            views.setOnClickPendingIntent(
                R.id.widgetMovieBackdrop,
                createNavigationIntent(context, movie)
            )

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun createRefreshIntent(context: Context, appWidgetId: Int): PendingIntent {
        val intent = Intent(context, McuMovieWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        return PendingIntent.getBroadcast(
            context,
            appWidgetId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createNavigationIntent(context: Context, movie: Movie): PendingIntent {
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.movieFragment)
            .setArguments(bundleOf("movieId" to movie.id))
            .createPendingIntent()
    }
}