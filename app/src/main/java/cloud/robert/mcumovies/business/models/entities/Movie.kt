package cloud.robert.mcumovies.business.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("releaseDate")
    val releaseDate: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("posterPath")
    val posterPath: String,
    @SerializedName("backdropPath")
    val backdropPath: String,
    @SerializedName("vote")
    val vote: Double,
    @SerializedName("actors")
    val actors: List<Int> = emptyList()
)