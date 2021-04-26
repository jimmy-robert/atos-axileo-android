package cloud.robert.mcumovies.business.models.relations

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "actorId"])
data class MovieActorCrossReference(
    val movieId: Int,
    val actorId: Int,
)