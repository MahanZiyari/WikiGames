package ir.mahan.wikigames.ui.home

import ir.mahan.wikigames.base.BaseView
import ir.mahan.wikigames.base.Basepresenter
import ir.mahan.wikigames.data.model.ResponseGamesList

interface HomeContracts {

    interface View: BaseView {
        fun showAllData(data: Map<Int, List<ResponseGamesList.Result>>)
        fun showLatestGamesOnCarousel(games: List<ResponseGamesList.Result>)
        fun setLoadingState(isShown: Boolean)
        fun showBestGamesByMetacritic(games: List<ResponseGamesList.Result>)
        fun showBestGamesShooter(games: List<ResponseGamesList.Result>)
    }

    interface Presenter: Basepresenter {
        fun getLatestGames()
        fun getBestGamesByMetacritic()
        fun getBestOfShooter()
        fun getAllData()
    }
}