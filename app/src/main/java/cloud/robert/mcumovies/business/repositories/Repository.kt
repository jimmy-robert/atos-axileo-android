package cloud.robert.mcumovies.business.repositories

import androidx.room.withTransaction
import cloud.robert.mcumovies.app.McuApp
import cloud.robert.mcumovies.business.databases.database.McuDatabase
import cloud.robert.mcumovies.business.models.relations.MovieActorCrossReference
import cloud.robert.mcumovies.business.network.McuApi

class Repository(
    private val api: McuApi = McuApp.api,
    private val db: McuDatabase = McuApp.db
) {
    suspend fun fetchData() {
        val response = api.fetchData()

        val crossReferences = response.movies.flatMap { movie ->
            movie.actors.map { actorId -> MovieActorCrossReference(movie.id, actorId) }
        }

        db.withTransaction {
            db.actorDao().apply {
                clean()
                insertAll(response.actors)
            }

            db.movieDao().apply {
                clean()
                insertAll(response.movies)
            }

            db.movieActorCrossReferenceDao().apply {
                clean()
                insertAll(crossReferences)
            }

        }
    }

    fun getMovies() = db.movieDao().getAll()
    fun getMovie(id: Int) = db.movieDao().get(id)

    fun getActors() = db.actorDao().getAll()
    fun getActor(id: Int) = db.actorDao().get(id)
}