package ir.mahan.wikigames.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ir.mahan.wikigames.data.model.GameEntity
import ir.mahan.wikigames.utils.GAMES_TABLE

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(entity: GameEntity): Completable

    @Update
    fun updateGame(entity: GameEntity): Completable

    @Delete
    fun deleteGame(entity: GameEntity): Completable

    @Query("select * from $GAMES_TABLE")
    fun getAllFavorites(): Single<List<GameEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM $GAMES_TABLE WHERE id == :id)")
    fun checkExistence(id: Int): Observable<Boolean>
}