package ir.mahan.wikigames.ui.games

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava3.flowable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ir.mahan.wikigames.base.BasePresenterImpl
import ir.mahan.wikigames.data.GamesPagingSource
import ir.mahan.wikigames.data.repository.GamesRepository
import javax.inject.Inject

class GamesPresenter @Inject constructor(
    private val repository: GamesRepository,
    private val view: GamesContract.View
) : GamesContract.Presenter, BasePresenterImpl() {


    override fun getRequestedGames(category: String, categoryId: Int) {
        getGamesByPaging(category, categoryId.toString())
    }

    private fun getGamesByPaging(type: String, typeID: String) {
        disposable = Pager(
            PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5
            )
        ) {
            GamesPagingSource(
                repository = repository,
                type = type,
                typeID = typeID
            )
        }.flowable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.loadGamesByPaging(it)
            }
    }


}