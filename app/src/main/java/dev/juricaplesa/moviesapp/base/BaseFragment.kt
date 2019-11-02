package dev.juricaplesa.moviesapp.base

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import dev.juricaplesa.moviesapp.R

/**
 * Created by Jurica Pleša
 */
open class BaseFragment : Fragment() {

    protected fun setActionBar(toolbar: Toolbar) {
        if (activity is AppCompatActivity) {
            val activity = activity as AppCompatActivity?
            activity?.setSupportActionBar(toolbar)
            val supportActionBar = activity?.supportActionBar
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    protected fun setHomeAsUp() {
        if (activity is AppCompatActivity) {
            val activity = activity as AppCompatActivity?
            val supportActionBar = activity?.supportActionBar
            supportActionBar.let {
                it?.setDisplayHomeAsUpEnabled(true)
                it?.setHomeButtonEnabled(true)
                it?.setHomeAsUpIndicator(R.drawable.ic_back)
            }
        }
    }

}