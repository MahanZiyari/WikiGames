package ir.mahan.wikigames.ui.search

import androidx.paging.PagingData
import ir.mahan.wikigames.base.BaseView
import ir.mahan.wikigames.base.Basepresenter
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.model.ResponseStores

interface SearchContracts {

    interface View: BaseView {
        fun showStores(stores: List<ResponseStores.Result>)
        fun showGenres(genres: List<ResponseStores.Result>)
        fun showSearchResultPaging(games: PagingData<ResponseGamesList.Result>)
        fun searchLoadingState(isShown: Boolean)
    }

    interface Presenter: Basepresenter {
        fun getAllStores()
        fun getAllGenres()
        fun searchInGames(query: String)
    }
}