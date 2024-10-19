package ir.mahan.wikigames.data.repository

import ir.mahan.wikigames.data.server.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiServices: ApiServices) {
    fun getAllStores() = apiServices.callAllStores()
    fun getAllGenres() = apiServices.callAllGenres()
}