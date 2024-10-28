package ir.mahan.wikigames.ui.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava3.flowable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.mahan.wikigames.base.BasePresenterImpl
import ir.mahan.wikigames.data.GamesPagingSource
import ir.mahan.wikigames.data.repository.SearchRepository
import ir.mahan.wikigames.utils.QueryParam
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
                    val randomGenres = it.results.shuffled().take(6)
                    view.showGenres(randomGenres)
                }
            }, {
                view.showGeneralError(it.message.toString())
            })
    }

    override fun searchInGames(query: String) {
        disposable = Pager(
            PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5
            )
        ) {
            GamesPagingSource(
                repository = repository,
                type = QueryParam.SEARCH.name,
                typeID = query
            )
        }.flowable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showSearchResultPaging(it)
            }

    }


}