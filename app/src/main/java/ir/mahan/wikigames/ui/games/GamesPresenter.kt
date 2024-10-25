package ir.mahan.wikigames.ui.games

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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

        disposable = repository
            .getGamesByGenre(genreId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.body()?.let {
                    view.handleLoadingState(false)
                    view.showRequestedGames(appendSummaryToGames(it.results))
                }
            }, {
                view.showGeneralError(it.message.toString())
            })
    }

    private fun getGamesByPlatform(platformId: Int) {
        view.handleLoadingState(true)

        disposable = repository
            .getGamesByPlatform(platformId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.body()?.let {
                    view.handleLoadingState(false)
                    debugLog("platforms games: ${it.results.size}")
                    view.showRequestedGames(appendSummaryToGames(it.results))
                }
            }, {
                view.showGeneralError(it.message.toString())
            })
    }

    private fun getGamesByStore(storeId: Int) {
        view.handleLoadingState(true)
        disposable = repository
            .getGamesByStore(storeId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.body()?.let {
                    view.handleLoadingState(false)
                    view.showRequestedGames(appendSummaryToGames(it.results))
                }
            }, {
                view.showGeneralError(it.message.toString())
            })
    }

    private fun appendSummaryToGames(games: List<ResponseGamesList.Result>): List<ResponseGamesList.Result> {
        val changedGames: MutableList<ResponseGamesList.Result> = games.toMutableList()

        changedGames
            .forEach {
                val call = repository.getGameDetails(it.id.toString())

                call.enqueue(object : retrofit2.Callback<ResponseGameDetails> {
                    override fun onResponse(
                        call: Call<ResponseGameDetails>,
                        response: Response<ResponseGameDetails>
                    ) {
                        response.body()?.let { details ->
                            it.description = details.descriptionRaw
                            Timber.e(it.description)
                        }
                    }

                    override fun onFailure(call: Call<ResponseGameDetails>, error: Throwable) {
                        view.showGeneralError(error.message.toString())
                    }

                })
            }

        return changedGames
    }


}