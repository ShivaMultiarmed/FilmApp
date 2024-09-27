package mikhail.shell.movie.app.activities

import android.os.Bundle
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
import mikhail.shell.movie.app.viewmodels.FilmViewModel
import com.google.android.material.snackbar.Snackbar
import mikhail.shell.movie.app.fragments.FilmFragment
import mikhail.shell.movie.app.models.Film
import mikhail.shell.movie.app.views.FilmCardView

class MainActivity: AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var viewModel: FilmViewModel
    private lateinit var B: MainActivityBinding
    private lateinit var loadingFragment: LoadingFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = MainActivityBinding.inflate(layoutInflater)
        setContentView(B.root)
        viewModel = ViewModelProvider(this).get(FilmViewModel::class.java)
        loadingFragment = LoadingFragment()
        openFragment(loadingFragment, false)
        requestAllFilms()
    }
    private fun openFragment(fragment: Fragment, addToBackStack: Boolean = true)
    {
        val transaction = supportFragmentManager.beginTransaction().replace(B.mainContainer.id, fragment)
        if (!addToBackStack)
            transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun requestAllFilms()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingFragment.setProgressBarEnabled(true)
            }
            val allFilms = viewModel.requestAllFilms()
            if (allFilms != null)
            {
                val allGenres = viewModel.fetchGenres() as List<String>
                withContext(Dispatchers.Main)
                {
                    val filmListFragment = FilmListFragment(allGenres, allFilms){ card ->
                        val filmCardView = card as FilmCardView
                        val film = filmCardView.getFilm()
                        openFragment(FilmFragment(film as Film))
                    }
                    openFragment(filmListFragment)
                }
            }
            else
            {
                withContext(Dispatchers.Main) {
                    loadingFragment.setProgressBarEnabled(false)
                    loadingFragment.view?.let {
                        Snackbar.make(it, "Ошибка при подлючении к Интернету.",Snackbar.LENGTH_LONG)
                            .setAction("Повторить"){ requestAllFilms() }
                    }?.show()
                }
            }

        }
    }
}