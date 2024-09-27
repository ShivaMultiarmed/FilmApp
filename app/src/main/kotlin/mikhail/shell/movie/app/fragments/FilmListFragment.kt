package mikhail.shell.movie.app.fragments

import mikhail.shell.movie.app.adapters.FilmListAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import mikhail.shell.movie.app.decorators.FilmListDecorator
import mikhail.shell.movie.app.R
import mikhail.shell.movie.app.activities.MainActivity
import mikhail.shell.movie.app.databinding.FilmListFragmentBinding
import mikhail.shell.movie.app.models.Film
import mikhail.shell.movie.app.views.FilmCardView

class FilmListFragment(private val genres: List<String>, private val films: List<Film>): Fragment() {
    private lateinit var adapter: FilmListAdapter
    private lateinit var B: FilmListFragmentBinding
    private var currentGenre: String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        B = FilmListFragmentBinding.inflate(inflater)
        return B.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FilmListAdapter(activity as Activity) { card ->
            val filmCard = card as FilmCardView
            openFilm(filmCard.getFilm() as Film)
        }
        B.filmsRecyclerVeiw.adapter = adapter
        B.filmsRecyclerVeiw.layoutManager = GridLayoutManager(activity, 2)
        val coeff = getDpToPxCoefficient()
        B.filmsRecyclerVeiw.addItemDecoration(FilmListDecorator(2, 0, 8, 8, 16, coeff))

        adapter.films = getAllFilms()

        getAllGenres().forEach(::addGenre)
    }
    private fun getDpToPxCoefficient() = context?.resources?.displayMetrics?.density?.toDouble() as Double
    private fun addGenre(genre: String) {
        val genre_view = layoutInflater.inflate(R.layout.radio_button, null) as RadioButton
        genre_view.layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        genre_view.text = genre
        genre_view.setOnClickListener { btn ->
            val radioButton = btn as RadioButton
            val newGenre = radioButton.text.toString()
            if (newGenre.equals(currentGenre))
            {
                radioButton.isChecked = false
                currentGenre = null
            }
            else
                currentGenre = newGenre

            adapter.films =
                if (currentGenre == null) getAllFilms()
                else filterFilmsByGenre(currentGenre as String)
        }
        B.genresRadioGroup.addView(genre_view)
    }
    private fun filterFilmsByGenre(genre: String) = getAllFilms().filter { film -> film.genres.contains(genre.toLowerCase()) }
    private fun getAllFilms() = films.sortedWith { m1, m2 -> m1.localized_name.compareTo(m2.localized_name) }
    private fun getAllGenres() = genres.sortedWith { g1, g2 -> g1.compareTo(g2) }.map(String::capitalize)
    private fun openFilm(film: Film)  {
        val intent = Intent(activity, MainActivity::class.java)
        val b = Bundle()
        b.putSerializable("film", film)
        intent.putExtras(b)
        startActivity(intent)
    }
}