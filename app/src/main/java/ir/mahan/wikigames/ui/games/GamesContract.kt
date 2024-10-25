package ir.mahan.wikigames.ui.games

import ir.mahan.wikigames.base.BaseView
import ir.mahan.wikigames.base.Basepresenter
import ir.mahan.wikigames.data.model.ResponseGamesList

interface GamesContract {

    interface View: BaseView {
        fun handleLoadingState(isShown: Boolean)
        fun showRequestedGames(games: List<ResponseGamesList.Result>)
    }

    interface Presenter: Basepresenter {
        fun getRequestedGames(category: String, categoryId: Int)
    }
}