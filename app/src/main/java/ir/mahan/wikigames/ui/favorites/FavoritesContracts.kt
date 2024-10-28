package ir.mahan.wikigames.ui.favorites

import androidx.paging.PagingData
import ir.mahan.wikigames.base.BaseView
import ir.mahan.wikigames.base.Basepresenter
import ir.mahan.wikigames.data.model.GameEntity
import ir.mahan.wikigames.data.model.ResponseGamesList

interface FavoritesContracts {

    interface View: BaseView {
        fun showAllFavoritesGames(games: List<GameEntity>)
        fun showEmptyScreen()
    }

    interface Presenter: Basepresenter {
        fun getAllFavoritesGames()
    }
}