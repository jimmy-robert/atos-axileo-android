package cloud.robert.mcumovies.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.business.models.entities.Actor
import cloud.robert.mcumovies.business.models.entities.Movie
import cloud.robert.mcumovies.business.models.relations.MovieWithActors
import cloud.robert.mcumovies.utils.defaults.DefaultViewHolder
import cloud.robert.mcumovies.utils.extensions.load
import cloud.robert.mcumovies.utils.extensions.toTmdbImageUrl
import cloud.robert.mcumovies.viewmodels.MovieViewModel
import cloud.robert.mcumovies.views.viewholders.ActorViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = Adapter(::navigateToActor)

        val movieList = view.findViewById<RecyclerView>(R.id.movieList)
        movieList.layoutManager = LinearLayoutManager(view.context)
        movieList.addItemDecoration(
            DividerItemDecoration(
                movieList.context,
                DividerItemDecoration.VERTICAL
            )
        )
        movieList.adapter = adapter

        val args: MovieFragmentArgs by navArgs()
        viewModel.getMovie(args.movieId).observe(viewLifecycleOwner) {
            adapter.setMovieWithActors(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun navigateToActor(actor: Actor) {
        val action = MovieFragmentDirections.actionMovieToActor(actor.id)
        findNavController().navigate(action)
    }

    private class Adapter(
        private val onItemClicked: (Actor) -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object {
            const val VIEW_TYPE_HEADER = 0
            const val VIEW_TYPE_ACTOR = 1
        }

        var items = emptyList<Any>()

        override fun getItemCount() = items.size

        override fun getItemViewType(position: Int) = when (items[position]) {
            is Movie -> VIEW_TYPE_HEADER
            else -> VIEW_TYPE_ACTOR
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
            VIEW_TYPE_HEADER -> MovieHeaderViewHolder(parent)
            else -> ActorViewHolder(parent).apply {
                itemView.setOnClickListener { onItemClicked(items[adapterPosition] as Actor) }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is MovieHeaderViewHolder) {
                holder.bindMovie(items[position] as Movie)
            } else if (holder is ActorViewHolder) {
                holder.bindActor(items[position] as Actor)
            }
        }

        fun setMovieWithActors(movieWithActors: MovieWithActors) {
            items = listOf(movieWithActors.movie, *movieWithActors.actors.toTypedArray())
        }
    }

    private class MovieHeaderViewHolder(parent: ViewGroup) :
        DefaultViewHolder(parent, R.layout.item_movie_header) {

        val backdrop: ImageView by viewById(R.id.movieHeaderBackdrop)
        val poster: ImageView by viewById(R.id.movieHeaderPosterImage)
        val year: TextView by viewById(R.id.movieHeaderYear)
        val title: TextView by viewById(R.id.movieHeaderTitle)
        val tagline: TextView by viewById(R.id.movieHeaderTagline)
        val overview: TextView by viewById(R.id.movieHeaderOverview)

        fun bindMovie(movie: Movie) {
            backdrop.load(movie.backdropPath.toTmdbImageUrl())
            poster.load(movie.posterPath.toTmdbImageUrl())
            year.text = movie.releaseDate.take(4)
            title.contentDescription = "Movie title: ${movie.title}"
            title.text = movie.title
            tagline.text = movie.tagline
            overview.text = movie.overview
        }
    }
}