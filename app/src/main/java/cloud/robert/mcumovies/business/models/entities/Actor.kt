package cloud.robert.mcumovies.business.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Actor(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("character")
    val character: String,
    @SerializedName("picturePath")
    val picturePath: String,
    @SerializedName("movies")
    val movies: List<Int> = emptyList()
)