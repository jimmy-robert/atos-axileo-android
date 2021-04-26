package cloud.robert.mcumovies.business.models.responses

import cloud.robert.mcumovies.business.models.entities.Actor
import cloud.robert.mcumovies.business.models.entities.Movie

data class ApiResponse(
    val movies: List<Movie>,
    val actors: List<Actor>
)
