package ir.mahan.wikigames.data.repository

import ir.mahan.wikigames.data.server.ApiServices
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val apiServices: ApiServices) {
    fun getDetailsForGameWith(gameId: String) = apiServices.observeGameDetails(gameId)
    fun getScreenshotsForGame(gameId: String) = apiServices.callForScreenshots(gameId)
    fun getSimilarGames(gameId: String) = apiServices.callForSimilarGames(gameId)
}