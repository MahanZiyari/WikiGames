package ir.mahan.wikigames.utils

import android.widget.ImageView
import androidx.paging.PagingSource.LoadResult
import coil.load
import coil.request.Disposable
import io.reactivex.rxjava3.core.Single
import ir.mahan.wikigames.data.model.ResponseGamesList

fun String.checkForEmptiness(): Boolean {
    return this.isNullOrEmpty().not()
        .and(
            this.isNullOrBlank().not()
        )
}

fun ImageView.loadByFade(url: String) {
    this.load(url){
        crossfade(true)
        crossfade(200)
    }
}
