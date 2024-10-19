package ir.mahan.wikigames.ui.search

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.mahan.wikigames.base.BasePresenterImpl
import ir.mahan.wikigames.base.Basepresenter
import ir.mahan.wikigames.data.repository.SearchRepository
import ir.mahan.wikigames.data.server.ApiServices
import org.joda.time.LocalTime
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

class SearchPresenter @Inject constructor(
    private val repository: SearchRepository,
    private val view: SearchContracts.View
) : SearchContracts.Presenter, BasePresenterImpl() {

    private val random = Random(19)

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
}