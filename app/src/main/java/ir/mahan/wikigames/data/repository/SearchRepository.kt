package ir.mahan.wikigames.data.repository

import androidx.paging.PagingSource
import ir.mahan.wikigames.base.BaseRepository
import ir.mahan.wikigames.data.server.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiServices: ApiServices): BaseRepository {
    fun getAllStores() = apiServices.callAllStores()
    fun getAllGenres() = apiServices.callAllGenres()
    fun searchGames(query: String, page: Int) = apiServices.callSearchGames(searchQuery = query, page = page)
    fun getGameDetails(gameId: String) = apiServices.observeGameDetails(gameId)
}