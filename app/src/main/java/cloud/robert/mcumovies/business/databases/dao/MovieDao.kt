package cloud.robert.mcumovies.business.databases.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cloud.robert.mcumovies.business.models.entities.Movie
import cloud.robert.mcumovies.business.models.relations.MovieWithActors

@Dao
interface MovieDao {
    @Insert
    suspend fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM movie")
    suspend fun clean()

    @Query("SELECT * FROM movie ORDER BY releaseDate")
    fun getAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun get(id: Int): LiveData<MovieWithActors>

    @Query("SELECT * FROM movie ORDER BY RANDOM()")
    suspend fun getRandom(): Movie
}