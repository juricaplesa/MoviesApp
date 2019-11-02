package dev.juricaplesa.moviesapp.search

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import dev.juricaplesa.moviesapp.App
import dev.juricaplesa.moviesapp.MainActivity
import dev.juricaplesa.moviesapp.R
import dev.juricaplesa.moviesapp.base.BaseFragment
import dev.juricaplesa.moviesapp.common.gone
import dev.juricaplesa.moviesapp.common.show
import dev.juricaplesa.moviesapp.common.utils.KeyboardUtils
import dev.juricaplesa.moviesapp.details.DetailsFragment
import dev.juricaplesa.moviesapp.models.Movie
import dev.juricaplesa.moviesapp.search.adapter.OnSearchItemClickListener
import dev.juricaplesa.moviesapp.search.adapter.SearchAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by Jurica Pleša
 */
class SearchFragment : BaseFragment(), SearchContract.View, OnSearchItemClickListener {

    private lateinit var presenter: SearchPresenter

    private val adapter: SearchAdapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SearchPresenter(App.apiProvider, Schedulers.io(), AndroidSchedulers.mainThread())
        presenter.injectView(this)
        adapter.setListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        setupSearchView()
    }

    private fun setupSearchView() {
        presenter.startObservingSearchTextChanges(RxTextView.textChanges(searchInput))
    }

    override fun showInitialMessage() {
        initialMessage.show()
    }

    override fun hideInitialMessage() {
        initialMessage.gone()
    }

    override fun showEmptyMessage() {
        emptyMessage.show()
    }

    override fun hideEmptyMessage() {
        emptyMessage.gone()
    }

    override fun showLoadingIndicator() {
        progressBar.show()
    }

    override fun hideLoadingIndicator() {
        progressBar.gone()
    }

    override fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.common_error_message), Toast.LENGTH_SHORT).show()
    }

    override fun setMovies(movies: List<Movie>) {
        adapter.setData(movies)
    }

    override fun clearMovies() {
        adapter.clearData()
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun onSearchItemClick(moviesImdbId: String) {
        activity.let {
            KeyboardUtils.hideKeyboard(activity as MainActivity)
        }
        (activity as MainActivity).addFragment(DetailsFragment.newInstance(moviesImdbId))
    }

    override fun onPause() {
        presenter.unsubscribe()
        super.onPause()
    }

}