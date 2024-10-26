package ir.mahan.wikigames.ui.games

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromIterable
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.mahan.wikigames.base.BasePresenterImpl
import ir.mahan.wikigames.data.model.ResponseGameDetails
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.repository.GamesRepository
import ir.mahan.wikigames.utils.debugLog
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class GamesPresenter @Inject constructor(
    private val repository: GamesRepository,
    private val view: GamesContract.View
) : GamesContract.Presenter, BasePresenterImpl() {

    private var games = mutableListOf<ResponseGamesList.Result>()

    override fun getRequestedGames(category: String, categoryId: Int) {
        when(category) {
            "genres" -> {
                getGamesByGenres(categoryId)
            }
            "stores" -> {
                getGamesByStore(categoryId)
            }
            else -> {
                getGamesByPlatform(categoryId)
            }
        }
    }

    private fun getGamesByGenres(genreId: Int) {
        view.handleLoadingState(true)
        disposable = repository.getGamesByGenre(genreId.toString())
            .flatMapObservable { gamesResponse ->
                gamesResponse.body()?.let {
                    games = it.results.toMutableList()
                }
                Observable.fromIterable(games)
            }
            .concatMap { game ->
                repository.getGameDetailsRX(game.id.toString())
                    .flatMap { detailsResponse ->
                        detailsResponse.body()?.let {
                            val details = it
                            game.description = details?.descriptionRaw ?: "No description available"
                        }
                        Single.just(game) // Emit the updated game
                    }
                    .toObservable()
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.handleLoadingState(false)
                view.showRequestedGames(it)
            }, {
                view.showGeneralError(it.message.toString())
            })
    }


    private fun getGamesByPlatform(platformId: Int) {
        view.handleLoadingState(true)
        disposable = repository.getGamesByPlatform(platformId.toString())
            .flatMapObservable { gamesResponse ->
                gamesResponse.body()?.let {
                    games = it.results.toMutableList()
                }
                Observable.fromIterable(games)
            }
            .concatMap { game ->
                repository.getGameDetailsRX(game.id.toString())
                    .flatMap { detailsResponse ->
                        detailsResponse.body()?.let {
                            val details = it
                            game.description = details?.descriptionRaw ?: "No description available"
                        }
                        Single.just(game) // Emit the updated game
                    }
                    .toObservable()
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.handleLoadingState(false)
                view.showRequestedGames(it)
            }, {
                view.showGeneralError(it.message.toString())
            })
    }

    private fun getGamesByStore(storeId: Int) {
        view.handleLoadingState(true)
        disposable = repository.getGamesByStore(storeId.toString())
            .flatMapObservable { gamesResponse ->
                gamesResponse.body()?.let {
                    games = it.results.toMutableList()
                }
                Observable.fromIterable(games)
            }
            .concatMap { game ->
                repository.getGameDetailsRX(game.id.toString())
                    .flatMap { detailsResponse ->
                        detailsResponse.body()?.let {
                            val details = it
                            game.description = details?.descriptionRaw ?: "No description available"
                        }
                        Single.just(game) // Emit the updated game
                    }
                    .toObservable()
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.handleLoadingState(false)
                view.showRequestedGames(it)
            }, {
                view.showGeneralError(it.message.toString())
            })
    }



}