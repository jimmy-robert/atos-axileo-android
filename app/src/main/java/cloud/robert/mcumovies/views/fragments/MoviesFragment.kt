package cloud.robert.mcumovies.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.business.models.entities.Movie
import cloud.robert.mcumovies.viewmodels.ActorViewModel
import cloud.robert.mcumovies.viewmodels.MainViewModel
import cloud.robert.mcumovies.views.viewholders.MovieViewHolder

class MoviesFragment : Fragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()

    private val actorViewModel by viewModels<ActorViewModel>({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = Adapter(::navigateToMovie)

        val moviesGrid = view.findViewById<RecyclerView>(R.id.moviesGrid)
        moviesGrid.layoutManager = GridLayoutManager(view.context, 2)
        moviesGrid.adapter = adapter

        val parent = parentFragment
        if (parent is ActorFragment) {
            actorViewModel.getActor(parent.actorId).observe(viewLifecycleOwner) {
                adapter.movies = it.movies
                adapter.notifyDataSetChanged()
            }
        }

        else {
            mainViewModel.movies.observe(viewLifecycleOwner) {
                adapter.movies = it
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun navigateToMovie(movie: Movie) {
        findNavController().navigate(R.id.movieFragment, bundleOf("movieId" to movie.id))
    }

    private class Adapter(
        private val onItemClicked: (Movie) -> Unit
    ) : RecyclerView.Adapter<MovieViewHolder>() {
        var movies = emptyList<Movie>()

        override fun getItemCount() = movies.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MovieViewHolder(parent).apply {
                itemView.setOnClickListener {
                    onItemClicked(movies[adapterPosition])
                }
            }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            holder.bindMovie(movies[position])
        }
    }
}