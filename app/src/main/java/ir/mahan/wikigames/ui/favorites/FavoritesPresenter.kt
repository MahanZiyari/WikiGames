package ir.mahan.wikigames.ui.favorites

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.mahan.wikigames.base.BasePresenterImpl
import ir.mahan.wikigames.data.database.GamesDao
import ir.mahan.wikigames.data.model.GameEntity
import ir.mahan.wikigames.data.repository.FavoritesRepository
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(
    private val repository: FavoritesRepository,
    private val view: FavoritesContracts.View
): FavoritesContracts.Presenter, BasePresenterImpl() {

    private val onSuccess: (List<GameEntity>) -> Unit = {
        if (it.isNotEmpty()) {
            view.showAllFavoritesGames(it)
        } else {
            view.showEmptyScreen()
        }
    }

    private val onError: (Throwable) -> Unit = {
        view.showGeneralError(it.message.toString())
    }

    override fun getAllFavoritesGames() {
        disposable = repository.getAllFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }


}