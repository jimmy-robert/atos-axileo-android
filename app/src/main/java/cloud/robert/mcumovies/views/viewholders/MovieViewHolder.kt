package cloud.robert.mcumovies.views.viewholders

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.business.models.entities.Movie
import cloud.robert.mcumovies.utils.defaults.DefaultViewHolder
import cloud.robert.mcumovies.utils.extensions.load
import cloud.robert.mcumovies.utils.extensions.toTmdbImageUrl

class MovieViewHolder(parent: ViewGroup) : DefaultViewHolder(parent, R.layout.item_movie_grid) {

    private val poster: ImageView by viewById(R.id.itemMoviePoster)
    private val title: TextView by viewById(R.id.itemMovieTitle)
    private val year: TextView by viewById(R.id.itemMovieYear)

    fun bindMovie(movie: Movie) {
        poster.load(movie.posterPath.toTmdbImageUrl())
        year.text = movie.releaseDate.take(4)
        title.text = movie.title
    }

}
