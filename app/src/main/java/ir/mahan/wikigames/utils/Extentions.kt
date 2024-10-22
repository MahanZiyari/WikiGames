package ir.mahan.wikigames.utils

import android.widget.ImageView
import coil.load
import coil.request.Disposable

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