package cloud.robert.mcumovies.business.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import cloud.robert.mcumovies.business.models.entities.Actor
import cloud.robert.mcumovies.business.models.entities.Movie

data class ActorWithMovies(
    @Embedded val actor: Actor,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            MovieActorCrossReference::class,
            parentColumn = "actorId",
            entityColumn = "movieId"
        )
    )
    val movies: List<Movie>
)