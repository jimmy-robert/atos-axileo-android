package cloud.robert.mcumovies.business.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Actor(
    @PrimaryKey
    var id: Int,
    var name: String,
    var character: String,
    var picturePath: String,
    var movies: List<Int> = emptyList()
)