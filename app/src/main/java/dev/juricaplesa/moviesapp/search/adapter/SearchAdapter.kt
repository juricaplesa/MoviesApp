package dev.juricaplesa.moviesapp.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import dev.juricaplesa.moviesapp.R
import dev.juricaplesa.moviesapp.models.Movie

/**
 * Created by Jurica Pleša
 */
class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    private var mMovies: ArrayList<Movie> = ArrayList()

    private lateinit var mOnSearchItemClickListener: OnSearchItemClickListener

    fun setListener(onSearchItemClickListener: OnSearchItemClickListener) {
        this.mOnSearchItemClickListener = onSearchItemClickListener
    }

    fun setData(data: List<Movie>) {
        mMovies.clear()
        mMovies.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        mMovies.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view, mOnSearchItemClickListener)
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    override fun onBindViewHolder(viewHolder: SearchViewHolder, position: Int) {
        val article = mMovies[position]
        viewHolder.setMovie(article)
    }

}