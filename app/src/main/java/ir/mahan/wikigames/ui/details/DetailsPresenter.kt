package ir.mahan.wikigames.ui.details

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.mahan.wikigames.base.BasePresenterImpl
import ir.mahan.wikigames.data.model.GameEntity
import ir.mahan.wikigames.data.model.ResponseGameDetails
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.model.ResponseScreenshots
import ir.mahan.wikigames.data.repository.DetailsRepository
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    private val repository: DetailsRepository,
    private val view: DetailsContract.View
) : DetailsContract.Presenter, BasePresenterImpl() {

    private lateinit var gameDetails: ResponseGameDetails
    private lateinit var screenshots: List<ResponseScreenshots.Result>
    private lateinit var franchise: List<ResponseGamesList.Result>

    override fun getGameDetailsFromAPI(gameId: Int) {
        view.handleLoadingState(true)
        disposable = repository.getDetailsForGameWith(gameId.toString())
            .subscribeOn(Schedulers.io())
            .flatMap {
                it.body()?.let { details ->
                    gameDetails = details
                }
                repository.getScreenshotsForGame(gameId.toString())
            }.flatMap {
                it.body()?.let { responseScreenshots ->
                    screenshots = responseScreenshots.results
                }
                repository.getSimilarGames(gameId.toString())
            }.map { it }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.body()?.let {
                    franchise = it.results
                    view.showDetails(
                        details = gameDetails,
                        screenshots = screenshots,
                        franchise = franchise
                    )
                    view.handleLoadingState(false)
                }
            },{
                view.showGeneralError(it.message.toString())
            })
    }

    override fun addGameToFavorites(entity: GameEntity) {
        disposable = repository.insertGameToDatabase(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.setFavoriteState(true)
            }
    }

    override fun removeGameFromFavorites(entity: GameEntity) {
        disposable = repository.deleteGamesFromDatabase(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.setFavoriteState(false)
            }
    }

    override fun checkForFavorite(id: Int) {
        disposable = repository.checkExistence(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.setFavoriteState(it)
            }
    }

}