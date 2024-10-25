package ir.mahan.wikigames.utils

import timber.log.Timber

fun debugLog(message: String) {
    Timber
        .tag(DEBUG_TAG)
        .log(1, message)
}