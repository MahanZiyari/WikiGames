package ir.mahan.wikigames.data.server

import io.reactivex.rxjava3.core.Single
import ir.mahan.wikigames.data.model.ResponseGamesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("games")
    fun callGamesList(
        @Query("key") apiKey: String,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callSearchGames(
        @Query("key") apiKey: String,
        @Query("search") searchQuery: String,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callForGamesByGenre(
        @Query("key") apiKey: String,
        @Query("genres") genre: String,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callForGamesByPlatform(
        @Query("key") apiKey: String,
        @Query("parent_platforms") parentPlatform: String,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callForNewestGames(
        @Query("key") apiKey: String,
        @Query("ordering") ordering: String,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callForBestRatedGames(
        @Query("key") apiKey: String,
        @Query("oredring") ordering: String = "-metacritic",
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    @GET("games")
    fun callForStoresGames(
        @Query("key") apiKey: String,
        @Query("stores") store: String,
        @Query("page") page: Int = 1
    ): Single<Response<ResponseGamesList>>

    // Genres APIs
    @GET("genres")
    fun callAllGenres(
        @Query("key") apiKey: String
    ): Single<Response<ResponseGamesList>>

}