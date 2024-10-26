package ir.mahan.wikigames.data.server

import io.reactivex.rxjava3.core.Single
import ir.mahan.wikigames.data.model.ResponseGameDetails
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.model.ResponseScreenshots
import ir.mahan.wikigames.data.model.ResponseStores
import ir.mahan.wikigames.utils.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("games")
    fun callGamesList(
        @Query("key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callSearchGames(
        @Query("key") apiKey: String = API_KEY,
        @Query("search") searchQuery: String,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callForGamesByGenre(
        @Query("key") apiKey: String = API_KEY,
        @Query("genres") genre: String,
        @Query("ordering") ordering: String = "-metacritic",
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callForGamesByPlatform(
        @Query("key") apiKey: String = API_KEY,
        @Query("parent_platforms") parentPlatform: String,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callForNewestGames(
        @Query("key") apiKey: String = API_KEY,
        @Query("dates") dates: String,
        @Query("ordering") ordering: String,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callForBestRatedGames(
        @Query("key") apiKey: String = API_KEY,
        @Query("oredring") ordering: String = "-metacritic",
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callForStoresGames(
        @Query("key") apiKey: String = API_KEY,
        @Query("stores") store: String,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>


    @GET("games/{id}")
    fun observeGameDetails(
        @Path("id") id : String,
        @Query("key") apiKey: String = API_KEY
    ): Single<Response<ResponseGameDetails>>

    @GET("games/{id}/game-series")
    fun callForSimilarGames(
        @Path("id") id : String,
        @Query("key") apiKey: String = API_KEY
    ): Single<Response<ResponseGamesList>>

    @GET("games/{id}/screenshots")
    fun callForScreenshots(
        @Path("id") id : String,
        @Query("key") apiKey: String = API_KEY
    ): Single<Response<ResponseScreenshots>>



    // Genres APIs
    @GET("genres")
    fun callAllGenres(
        @Query("key") apiKey: String = API_KEY
    ): Single<Response<ResponseStores>>

    // Stores APIs
    @GET("stores")
    fun callAllStores(
        @Query("key") apiKey: String = API_KEY
    ): Single<Response<ResponseStores>>

}