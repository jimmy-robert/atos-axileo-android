package cloud.robert.mcumovies.business.databases.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cloud.robert.mcumovies.business.models.entities.Actor
import cloud.robert.mcumovies.business.models.relations.ActorWithMovies

@Dao
interface ActorDao {
    @Insert
    suspend fun insertAll(actors: List<Actor>)

    @Query("DELETE FROM actor")
    suspend fun clean()

    @Query("SELECT * FROM actor ORDER BY name")
    fun getAll(): LiveData<List<Actor>>

    @Query("SELECT * FROM actor WHERE id = :id")
    fun get(id: Int) : LiveData<ActorWithMovies>

    @Query("SELECT * FROM actor WHERE id = :id")
    suspend fun getAsync(id: Int) : ActorWithMovies
}