package ir.mahan.wikigames.data

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.mahan.wikigames.base.BaseRepository
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.repository.GamesRepository
import ir.mahan.wikigames.data.repository.SearchRepository
import ir.mahan.wikigames.utils.QueryParam
import javax.inject.Inject

class GamesPagingSource @Inject constructor(
    private val repository: BaseRepository,
    private val type: String,
    private val typeID: String,
) : RxPagingSource<Int, ResponseGamesList.Result>() {

    override fun getRefreshKey(state: PagingState<Int, ResponseGamesList.Result>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ResponseGamesList.Result>> {
        //debugLog("load Single Called")
        val currentPage = params.key ?: 1
        return when (type) {
            QueryParam.GENRES.name -> {
                val repo = repository as GamesRepository
                repo.getGamesByGenre(
                    genreId = typeID,
                    page = currentPage
                )
                    .subscribeOn(Schedulers.io())
                    .flatMapObservable {
                        val games = it.body()?.results ?: emptyList()
                        //debugLog("Naked Games: ${games.size}")
                        Observable.fromIterable(games)
                    }
                    .concatMap { game ->
                        repository.getGameDetailsRX(game.id.toString())
                            .flatMap { detailsResponse ->
                                detailsResponse.body()?.let {
                                    val details = it
                                    game.description =
                                        details.descriptionRaw ?: "No description available"
                                }
                                Single.just(game)
                            }.toObservable()
                    }
                    .toList()
                    .map {
                        val games = it.shuffled()
                        //debugLog("Dressed Games: ${games.size}")
                        toLoadResult(games, currentPage)
                    }.onErrorReturn { LoadResult.Error(it) }
            }// genres Scope

            QueryParam.STORES.name -> {
                val repo = repository as GamesRepository
                repo.getGamesByStore(
                    storeId = typeID,
                    page = currentPage
                )
                    .subscribeOn(Schedulers.io())
                    .flatMapObservable {
                        val games = it.body()?.results ?: emptyList()
                        //debugLog("Naked Games: ${games.size}")
                        Observable.fromIterable(games)
                    }
                    .concatMap { game ->
                        repository.getGameDetailsRX(game.id.toString())
                            .flatMap { detailsResponse ->
                                detailsResponse.body()?.let {
                                    val details = it
                                    game.description =
                                        details.descriptionRaw ?: "No description available"
                                }
                                Single.just(game)
                            }.toObservable()
                    }
                    .toList()
                    .map {
                        val games = it.shuffled()
                        //debugLog("Dressed Games: ${games.size}")
                        toLoadResult(games, currentPage)
                    }.onErrorReturn { LoadResult.Error(it) }
            }// stores Scope

            QueryParam.PARENT_PLATFORMS.name -> {
                val repo = repository as GamesRepository
                repo.getGamesByPlatform(
                    platformId = typeID,
                    page = currentPage
                )
                    .subscribeOn(Schedulers.io())
                    .flatMapObservable {
                        val games = it.body()?.results ?: emptyList()
                        //debugLog("Naked Games: ${games.size}")
                        Observable.fromIterable(games)
                    }
                    .concatMap { game ->
                        repository.getGameDetailsRX(game.id.toString())
                            .flatMap { detailsResponse ->
                                detailsResponse.body()?.let {
                                    val details = it
                                    game.description =
                                        details.descriptionRaw ?: "No description available"
                                }
                                Single.just(game)
                            }.toObservable()
                    }
                    .toList()
                    .map {
                        val games = it
                        //debugLog("Dressed Games: ${games.size}")
                        toLoadResult(games, currentPage)
                    }.onErrorReturn { LoadResult.Error(it) }
            }// platforms Scope

            QueryParam.SEARCH.name -> {
                val repo = repository as SearchRepository
                repo.searchGames(
                    query = typeID,
                    page = currentPage
                )
                    .subscribeOn(Schedulers.io())
                    .flatMapObservable {
                        val games = it.body()?.results ?: emptyList()
                        //debugLog("Naked Games: ${games.size}")
                        Observable.fromIterable(games)
                    }
                    .concatMap { game ->
                        repository.getGameDetails(game.id.toString())
                            .flatMap { detailsResponse ->
                                detailsResponse.body()?.let {
                                    val details = it
                                    game.description = details.descriptionRaw ?: "No description available"
                                }
                                Single.just(game)
                            }.toObservable()
                    }
                    .toList()
                    .map {
                        val games = it.shuffled()
                        //debugLog("Dressed Games: ${games.size}")
                        toLoadResult(games, currentPage)
                    }.onErrorReturn { LoadResult.Error(it) }
            }

            else -> {
                val repo = repository as GamesRepository
                repo.getAllGames(
                    page = currentPage
                )
                    .subscribeOn(Schedulers.io())
                    .flatMapObservable {
                        val games = it.body()?.results ?: emptyList()
                        //debugLog("Naked Games: ${games.size}")
                        Observable.fromIterable(games)
                    }
                    .concatMap { game ->
                        repository.getGameDetailsRX(game.id.toString())
                            .flatMap { detailsResponse ->
                                detailsResponse.body()?.let {
                                    val details = it
                                    game.description = details.descriptionRaw ?: "No description available"
                                }
                                Single.just(game)
                            }.toObservable()
                    }
                    .toList()
                    .map {
                        val games = it.shuffled()
                        //debugLog("Dressed Games: ${games.size}")
                        toLoadResult(games, currentPage)
                    }.onErrorReturn { LoadResult.Error(it) }
            }
        }
    }


    private fun toLoadResult(
        data: List<ResponseGamesList.Result>,
        position: Int
    ): LoadResult<Int, ResponseGamesList.Result> {
        //debugLog("Data size: ${data.size}\n position: $position\n")
        return LoadResult.Page(
            data = data,
            prevKey = if (position == 1) null else position - 1,
            nextKey = position.plus(1)
        )
    }

}