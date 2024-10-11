package ir.mahan.wikigames.base

import io.reactivex.rxjava3.disposables.Disposable

open class BasePresenterImpl : Basepresenter {
    var disposable: Disposable? = null

    override fun onStop() {
        disposable?.let {
            it.dispose()
        }
    }
}