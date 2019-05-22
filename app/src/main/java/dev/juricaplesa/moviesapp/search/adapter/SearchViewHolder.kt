package dev.juricaplesa.moviesapp.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import dev.juricaplesa.moviesapp.models.Movie
import kotlinx.android.synthetic.main.item_search.view.*

/**
 * Created by Jurica Pleša
 */
class SearchViewHolder(itemView: View, private val onSearchItemClickListener: OnSearchItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

    fun setMovie(movie: Movie) = with(itemView) {
        itemView.name.text = movie.title

        itemView.setOnClickListener { onSearchItemClickListener.onSearchItemClick(movie.imdbId) }
    }

}