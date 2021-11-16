package com.`fun`.gallerydroid.common

import android.widget.ImageView
import coil.Coil
import coil.ImageLoader
import coil.api.load
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable

inline fun ImageView.load(
    uri: String?,
    imageLoader: ImageLoader = Coil.loader(),
    builder: LoadRequestBuilder.() -> Unit = {}
): RequestDisposable {
    return imageLoader.load(context, uri) {
        target(this@load)
        builder()
    }
}