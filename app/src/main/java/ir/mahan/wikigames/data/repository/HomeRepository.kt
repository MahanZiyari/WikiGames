package ir.mahan.wikigames.data.repository

import ir.mahan.wikigames.data.server.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiServices: ApiServices) {
    fun getLatestGames(date: String, ordering: String) = apiServices.callForNewestGames(
        dates = date,
        ordering = ordering
    )

    fun getBestGamesByMetacritic() = apiServices.callForBestRatedGames()
    fun getBestOfSpecificGenre(genre: String, ordering: String) = apiServices.callForGamesByGenre(
        genre = genre,
        ordering = ordering
    )
}