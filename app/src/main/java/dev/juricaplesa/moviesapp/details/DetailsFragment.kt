package dev.juricaplesa.moviesapp.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import dev.juricaplesa.moviesapp.R
import dev.juricaplesa.moviesapp.base.BaseFragment
import dev.juricaplesa.moviesapp.common.EXTRA_MOVIES_IMDB_ID
import dev.juricaplesa.moviesapp.common.gone
import dev.juricaplesa.moviesapp.common.show
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

/**
 * Created by Jurica Pleša
 */
class DetailsFragment : BaseFragment(), DetailsContract.View {

    @Inject
    lateinit var presenter: DetailsContract.Presenter

    private lateinit var moviesImdbId: String

    companion object {
        fun newInstance(moviesImdbId: String): DetailsFragment {
            val detailsFragment = DetailsFragment()

            val bundle = Bundle()
            bundle.putString(EXTRA_MOVIES_IMDB_ID, moviesImdbId)
            detailsFragment.arguments = bundle

            return detailsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        presenter.injectView(this)

        val bundle = arguments
        bundle?.let { moviesImdbId = bundle.getString(EXTRA_MOVIES_IMDB_ID, "") }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        presenter.getMovieDetails(moviesImdbId)
    }

    private fun setupToolbar() {
        setActionBar(toolbar)
        setHomeAsUp()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
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

    override fun displayTitle(title: String) {
        toolbarTitle.text = title
    }

    override fun displayYear(year: String) {
        yearTxt.text = year
    }

    override fun displayGenre(genre: String) {
        genreTxt.text = genre
    }

    override fun displayDirector(director: String) {
        directorTxt.text = director
    }

    override fun displayActors(actors: String) {
        actorsTxt.text = actors
    }

    override fun displayPlot(plot: String) {
        plotTxt.text = plot
    }

    override fun displayPoster(posterUrl: String) {
        Glide.with(this)
            .load(posterUrl)
            .centerInside()
            .placeholder(R.drawable.ic_image_placeholder)
            .into(poster)
    }

    override fun isActive(): Boolean {
        return isAdded
    }

}