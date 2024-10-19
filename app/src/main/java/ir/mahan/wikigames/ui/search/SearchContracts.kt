package ir.mahan.wikigames.ui.search

import ir.mahan.wikigames.base.BaseView
import ir.mahan.wikigames.base.Basepresenter
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.model.ResponseStores

interface SearchContracts {

    interface View: BaseView {
        fun showStores(stores: List<ResponseStores.Result>)
        fun showGenres(genres: List<ResponseStores.Result>)
    }

    interface Presenter: Basepresenter {
        fun getAllStores()
        fun getAllGenres()
    }
}