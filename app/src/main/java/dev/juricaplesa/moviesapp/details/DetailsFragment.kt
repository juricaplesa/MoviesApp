package dev.juricaplesa.moviesapp.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import dev.juricaplesa.moviesapp.common.getViewModelFactory
import dev.juricaplesa.moviesapp.databinding.FragmentDetailsBinding

/**
 * Created by Jurica Pleša
 */
class DetailsFragment : Fragment() {

    private val viewModel by viewModels<DetailsViewModel> { getViewModelFactory() }

    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var viewDataBinding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }.apply {
            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
        })

        viewModel.getMovieDetails(args.moviesImdbId)
    }

}