package dev.juricaplesa.moviesapp.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import dev.juricaplesa.moviesapp.R

/**
 * Created by Jurica Pleša
 */
@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .centerInside()
            .placeholder(R.drawable.ic_image_placeholder)
            .into(view)
    }
}