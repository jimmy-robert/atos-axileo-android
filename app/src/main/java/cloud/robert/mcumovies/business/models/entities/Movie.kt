package cloud.robert.mcumovies.business.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    val id: Int,
    val genres: List<String>,
    val title: String,
    var releaseDate: String,
    var overview: String,
    var tagline: String,
    var posterPath: String,
    var backdropPath: String,
    var vote: Double,
    var actors: List<Int>
)