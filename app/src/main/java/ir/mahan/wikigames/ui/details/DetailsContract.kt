package ir.mahan.wikigames.ui.details

import ir.mahan.wikigames.base.BaseView
import ir.mahan.wikigames.base.Basepresenter
import ir.mahan.wikigames.data.model.GameEntity
import ir.mahan.wikigames.data.model.ResponseGameDetails
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.model.ResponseScreenshots

interface DetailsContract {

    interface View : BaseView {
        fun handleLoadingState(isShown: Boolean)
        fun showDetails(
            details: ResponseGameDetails,
            screenshots: List<ResponseScreenshots.Result>,
            franchise: List<ResponseGamesList.Result>
        )
        fun setFavoriteState(isAdded: Boolean)
    }

    interface Presenter : Basepresenter {
        fun getGameDetailsFromAPI(gameId: Int)
        fun addGameToFavorites(entity: GameEntity)
        fun removeGameFromFavorites(entity: GameEntity)
        fun checkForFavorite(id: Int)
    }
}