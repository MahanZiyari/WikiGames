package ir.mahan.wikigames.ui.search

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.mahan.wikigames.base.BasePresenterImpl
import ir.mahan.wikigames.data.model.ResponseGameDetails
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.repository.SearchRepository
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val repository: SearchRepository,
    private val view: SearchContracts.View
) : SearchContracts.Presenter, BasePresenterImpl() {


    override fun getAllStores() {
        disposable = repository
            .getAllStores()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.body()?.let {
                    //TODO: Hide Loading
                    val randomStores = it.results.shuffled().take(6)
                    view.showStores(randomStores)
                }
            }, {
                view.showGeneralError(it.message.toString())
            })
    }

    override fun getAllGenres() {
        disposable = repository
            .getAllGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.body()?.let {
                    //TODO: Hide Loading
                    val randomGenres = it.results.shuffled().take(6)
                    view.showGenres(randomGenres)
                    Timber.tag("DEBUG").e("result: ${it.results}")
                }
            }, {
                view.showGeneralError(it.message.toString())
            })
    }

    override fun searchInGames(query: String) {
        view.searchLoadingState(true)
        disposable = repository.searchGames(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.body()?.let {
                    // Getting Description
                    view.searchLoadingState(false)
//                    view.showSearchResult(it.results)
                    appendSummaryToGames(it.results)
                }
            }, {
                view.showGeneralError(it.message.toString())
            })
    }

    private fun appendSummaryToGames(games: List<ResponseGamesList.Result>) {
        val changedGames: MutableList<ResponseGamesList.Result> = games.toMutableList()

        changedGames
            .map {
                it.description = ""
                it
            }
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

        view.showSearchResult(changedGames)
    }


}