package mikhail.shell.movie.app.activities

import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mikhail.shell.movie.app.databinding.MainActivityBinding
import mikhail.shell.movie.app.fragments.FilmListFragment
import mikhail.shell.movie.app.fragments.LoadingFragment
import mikhail.shell.movie.app.viewmodels.FilmViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.async
import mikhail.shell.movie.app.FilmCatalog
import mikhail.shell.movie.app.R
import mikhail.shell.movie.app.fragments.FilmFragment
import mikhail.shell.movie.app.models.Film
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity: AppCompatActivity(), FilmCatalog {
    private lateinit var viewModel: FilmViewModel
    private lateinit var B: MainActivityBinding
    private lateinit var loadingFragment: LoadingFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = MainActivityBinding.inflate(layoutInflater)
        setContentView(B.root)
        viewModel = getViewModel<FilmViewModel>()
        loadingFragment = LoadingFragment()
        openFragment(loadingFragment)
        requestAllFilms()
        setUpPicasso()
        setUpSupportActionBar()
    }
    private fun openFragment(fragment: Fragment, addToBackStack: Boolean = true)
    {
        val transaction = supportFragmentManager.beginTransaction().replace(B.mainContainer.id, fragment)
        if (addToBackStack)
            transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun requestAllFilms()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            async { viewModel.requestAllFilms() } .await()
            withContext(Dispatchers.Main) {
                val allFilms = viewModel.getAllFilms()
                loadingFragment.setProgressBarEnabled(true)
                if (allFilms == null)
                    displayOnLoadingErrorScreen()
                else {
                    val allGenres = viewModel.getGenres()!!
                    val filmListFragment = FilmListFragment(allGenres, allFilms)
                    openFragment(filmListFragment, false)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId)
        {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        B.headerTitle.text = resources.getString(R.string.film_list_title)
        super.onBackPressed()
    }
    private fun setUpPicasso()
    {
        val picasso = Picasso.Builder(this)
            .downloader(get<Downloader>())
            .build()
        Picasso.setSingletonInstance(picasso)
    }
    private fun setUpSupportActionBar()
    {
        setSupportActionBar(B.appBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }
    private fun setUpFilmToolbar(film: Film)
    {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        B.appBar.navigationIcon?.setTint(resources.getColor(R.color.white))
        B.headerTitle.text = film.name
    }
    private fun getColorById(colorId: Int) : Int
    {
        val colorData = TypedValue()
        theme.resolveAttribute(colorId, colorData, true)
        return colorData.data
    }
    private fun displayOnLoadingErrorScreen() {
        loadingFragment.setProgressBarEnabled(false)
        loadingFragment.view?.let {
            val color = getColorById(androidx.appcompat.R.attr.colorAccent)
            Snackbar.make(it, "Ошибка подключения сети",Snackbar.LENGTH_INDEFINITE)
                .setAction("Повторить"){ requestAllFilms() }
                .setActionTextColor(color)
        }?.show()
    }

    override fun openFilm(film: Film) {
        openFragment(FilmFragment(film))
        setUpFilmToolbar(film)
    }
}