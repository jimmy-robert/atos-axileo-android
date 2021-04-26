package cloud.robert.mcumovies.business.databases.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cloud.robert.mcumovies.business.models.relations.MovieActorCrossReference

@Dao
interface MovieActorCrossReferenceDao {
    @Insert
    suspend fun insertAll(crossReferences: List<MovieActorCrossReference>)

    @Query("DELETE FROM movieactorcrossreference")
    suspend fun clean()
}