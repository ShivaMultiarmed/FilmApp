package mikhail.shell.movie.app

import android.os.Bundle
import android.util.Log
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mikhail.shell.movie.app.databinding.MainActivityBinding
import mikhail.shell.movie.app.fragments.FilmListFragment
import mikhail.shell.movie.app.fragments.LoadingFragment
import mikhail.shell.movie.app.models.Film
import mikhail.shell.movie.app.viewmodels.FilmViewModel

class MainActivity: AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var viewModel: FilmViewModel
    private lateinit var B: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = MainActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.main_activity)
        viewModel = ViewModelProvider(this).get(FilmViewModel::class.java)
        openFragment(LoadingFragment(), false)
        requestAllFilms()
    }
    private fun openFragment(fragment: Fragment, addToBackStack: Boolean = true)
    {
        val transaction = supportFragmentManager.beginTransaction().replace(B.mainContainer.id, fragment)
        if (addToBackStack)
            transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun goToPreviousFragment()
    {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
    }
    private fun requestAllFilms()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.requestAllFilms()
            val allFilms = viewModel.getAllFilms()
            if (allFilms != null)
                withContext(Dispatchers.Main)
                {
                    val filmListFragment = FilmListFragment()
                    openFragment(filmListFragment)
                    allFilms.forEach(filmListFragment::addFilm)
                }
        }
    }
}