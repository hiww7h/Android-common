package com.ww7h.ww.common.apis.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zhihu.matisse.engine.ImageEngine


class GlideEngine : ImageEngine {

    companion object {

        val instance: GlideEngine
            get() = GlideEngineInstance.INSTANCE
    }

    internal object GlideEngineInstance {
        val INSTANCE = GlideEngine()
    }

    override fun loadThumbnail(context: Context, resize: Int, placeholder: Drawable, imageView: ImageView, uri: Uri) {
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .into(imageView)
    }

    override fun loadGifThumbnail(
        context: Context, resize: Int, placeholder: Drawable, imageView: ImageView,
        uri: Uri
    ) {
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .into(imageView)
    }

    override fun loadImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri) {
        Glide.with(context)
            .load(uri)
            .into(imageView)
    }

    override fun loadGifImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri) {
        Glide.with(context)
            .asGif()
            .load(uri)
            .into(imageView)

    }

    override fun supportAnimatedGif(): Boolean {
        return true
    }

}

