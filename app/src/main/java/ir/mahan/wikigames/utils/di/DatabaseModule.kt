package ir.mahan.wikigames.utils.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.mahan.wikigames.data.database.GamesDb
import ir.mahan.wikigames.data.model.GameEntity
import ir.mahan.wikigames.utils.GAME_DATABASE

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideEntity(): GameEntity = GameEntity()

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context = context,
        klass = GamesDb::class.java,
        name = GAME_DATABASE
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    fun provideGamesDao(db: GamesDb) = db.dao()
}