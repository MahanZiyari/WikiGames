package ir.mahan.wikigames.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.mahan.wikigames.data.database.typeconverters.Converters
import ir.mahan.wikigames.data.model.GameEntity

@Database(entities = [GameEntity::class], version = 1, exportSchema = false)
@TypeConverters(value = [Converters::class])
abstract class GamesDb: RoomDatabase() {
    abstract fun dao(): GamesDao
}