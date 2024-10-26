package ir.mahan.wikigames.data.repository

import ir.mahan.wikigames.data.server.ApiServices
import javax.inject.Inject

class GamesRepository @Inject constructor(private val apiServices: ApiServices) {
    fun getGamesByGenre(genreId: String) = apiServices.callForGamesByGenre(genre = genreId)
    fun getGamesByPlatform(platformId: String) = apiServices.callForGamesByPlatform(parentPlatform = platformId)
    fun getGamesByStore(storeId: String) = apiServices.callForStoresGames(store = storeId)
    fun getGameDetailsRX(gameId: String) = apiServices.observeGameDetails(gameId)
}