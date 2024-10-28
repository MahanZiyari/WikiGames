package ir.mahan.wikigames.data.repository

import ir.mahan.wikigames.data.database.GamesDao
import ir.mahan.wikigames.data.model.GameEntity
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val dao: GamesDao) {
    fun getAllFavorites() = dao.getAllFavorites()
}