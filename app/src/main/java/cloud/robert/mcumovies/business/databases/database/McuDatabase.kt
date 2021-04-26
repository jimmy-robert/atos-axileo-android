package cloud.robert.mcumovies.business.databases.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cloud.robert.mcumovies.business.databases.converters.IntListConverter
import cloud.robert.mcumovies.business.databases.converters.StringListConverter
import cloud.robert.mcumovies.business.databases.dao.ActorDao
import cloud.robert.mcumovies.business.databases.dao.MovieActorCrossReferenceDao
import cloud.robert.mcumovies.business.databases.dao.MovieDao
import cloud.robert.mcumovies.business.models.entities.Actor
import cloud.robert.mcumovies.business.models.entities.Movie
import cloud.robert.mcumovies.business.models.relations.MovieActorCrossReference

@Database(
    version = 1,
    entities = [
        Movie::class,
        Actor::class,
        MovieActorCrossReference::class
    ]
)
@TypeConverters(
    StringListConverter::class,
    IntListConverter::class
)
abstract class McuDatabase : RoomDatabase() {
    abstract fun actorDao(): ActorDao
    abstract fun movieDao(): MovieDao
    abstract fun movieActorCrossReferenceDao() : MovieActorCrossReferenceDao
}