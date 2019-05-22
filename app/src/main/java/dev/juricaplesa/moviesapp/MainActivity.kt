package dev.juricaplesa.moviesapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import dev.juricaplesa.moviesapp.search.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, SearchFragment())
                .commit()
    }

    @Suppress("UNNECESSARY_SAFE_CALL")
    fun addFragment(fragment: Fragment) {
        fragment?.let {
            supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.main_fragment_container, fragment)
                    .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

}
