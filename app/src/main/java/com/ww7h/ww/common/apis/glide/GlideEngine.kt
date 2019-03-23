package com.ww7h.ww.common.apis.glide

/**
 * Created by ww on 2018/7/23.
 */

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zhihu.matisse.engine.ImageEngine

/**
 * [ImageEngine] implementation using Glide.
 */

class GlideEngine : ImageEngine {

    override fun loadThumbnail(context: Context, resize: Int, placeholder: Drawable, imageView: ImageView, uri: Uri) {
        Glide.with(context)
            .asBitmap()  // some .jpeg files are actually gif
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

