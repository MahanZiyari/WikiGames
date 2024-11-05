package ir.mahan.wikigames.ui.home

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.mahan.wikigames.base.BasePresenterImpl
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.repository.HomeRepository
import org.joda.time.LocalDate
import timber.log.Timber
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val repository: HomeRepository,
    private val view: HomeContracts.View
) : HomeContracts.Presenter, BasePresenterImpl() {

    private val apiResult: MutableMap<Int, List<ResponseGamesList.Result>> = mutableMapOf()


    override fun getHomeScreenData() {
        view.setLoadingState(true)
        disposable =
            repository.getLatestGames(date = LocalDate.now().toString(), ordering = "released")
                .subscribeOn(Schedulers.io())
                .cache()
                .flatMap {
                    it.body()?.let { apiResult.put(1, filterGamesByBackGround(it.results)) }
                    repository.getBestGamesByMetacritic().cache()
                }.flatMap {
                    it.body()?.let { apiResult.put(2, it.results) }
                    repository.getBestOfSpecificGenre("2", "-metacritic").cache()
                }.map { it }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    body.body()?.let { bestShooters ->
                        apiResult[3] = bestShooters.results
                        view.setLoadingState(false)
                        view.showAllData(apiResult)
                    }
                }, {
                    view.showGeneralError(it.message.toString())
                })
    }

    private fun filterGamesByBackGround(list: List<ResponseGamesList.Result>): List<ResponseGamesList.Result> {
        val newList = list.filter { it.backgroundImage != null }.take(5)
        return newList
    }

}