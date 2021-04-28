package cloud.robert.mcumovies.business.models.responses

import cloud.robert.mcumovies.business.models.entities.Actor
import cloud.robert.mcumovies.business.models.entities.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("movies")
    val movies: List<Movie>,
    @SerializedName("actors")
    val actors: List<Actor>
)
