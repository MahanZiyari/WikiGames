package ir.mahan.wikigames.data.repository

import ir.mahan.wikigames.base.BaseRepository
import ir.mahan.wikigames.data.server.ApiServices
import javax.inject.Inject

class GamesRepository @Inject constructor(private val apiServices: ApiServices): BaseRepository {
    fun getGamesByGenre(genreId: String, page: Int) = apiServices.callForGamesByGenre(genre = genreId, page = page)
    fun getGamesByPlatform(platformId: String, page: Int) = apiServices.callForGamesByPlatform(parentPlatform = platformId, page = page)
    fun getGamesByStore(storeId: String, page: Int) = apiServices.callForStoresGames(store = storeId,  page = page)
    fun getGameDetailsRX(gameId: String) = apiServices.observeGameDetails(gameId)
    fun getAllGames(page: Int) = apiServices.callGamesList(page = page)
}