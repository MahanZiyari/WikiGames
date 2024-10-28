package ir.mahan.wikigames.data.repository

import ir.mahan.wikigames.data.database.GamesDao
import ir.mahan.wikigames.data.model.GameEntity
import ir.mahan.wikigames.data.server.ApiServices
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val dao: GamesDao
) {
    fun getDetailsForGameWith(gameId: String) = apiServices.observeGameDetails(gameId)
    fun getScreenshotsForGame(gameId: String) = apiServices.callForScreenshots(gameId)
    fun getSimilarGames(gameId: String) = apiServices.callForSimilarGames(gameId)
    // Database
    fun insertGameToDatabase(entity: GameEntity) = dao.insertGame(entity)
    fun deleteGamesFromDatabase(entity: GameEntity) = dao.deleteGame(entity)
    fun checkExistence(id: Int) = dao.checkExistence(id)
}