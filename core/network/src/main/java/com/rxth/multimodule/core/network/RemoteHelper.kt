package com.rxth.multimodule.core.network

object RemoteHelper {
    fun getImagePath (path: String) = "${BuildConfig.IMAGE_URL}$path"
}