package mikhail.shell.movie.app.fragments

import mikhail.shell.movie.app.adapters.FilmListAdapter
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import mikhail.shell.movie.app.decorators.FilmListDecorator
import mikhail.shell.movie.app.R
import mikhail.shell.movie.app.databinding.FilmListFragmentBinding
import mikhail.shell.movie.app.models.Film
import mikhail.shell.movie.app.viewmodels.FilmViewModel
import org.koin.androidx.viewmodel.ext.android.getActivityViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class FilmListFragment(private val genres: List<String>, private val films: List<Film>): Fragment() {
    private lateinit var adapter: FilmListAdapter
    private lateinit var B: FilmListFragmentBinding
    private var currentGenre: String? = null
    private lateinit var viewModel: FilmViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        B = FilmListFragmentBinding.inflate(inflater)
        return B.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getActivityViewModel ()
        setUpRecyclerView()
        adapter.films?.addAll(viewModel.getAllFilms() as Collection<Film>)
        viewModel.getGenres()?.forEach(::addGenre)
    }
    private fun getDpToPxCoefficient() = context?.resources?.displayMetrics?.density?.toDouble() as Double
    private fun addGenre(genre: String) {
        val genre_view = layoutInflater.inflate(R.layout.radio_button, null) as RadioButton
        genre_view.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        genre_view.text = genre
        genre_view.setOnClickListener(onClickListener)
        B.genresRadioGroup.addView(genre_view)
    }
    private fun setUpRecyclerView()
    {
        adapter = FilmListAdapter(activity as Activity)
        B.filmsRecyclerVeiw.adapter = adapter
        B.filmsRecyclerVeiw.layoutManager = GridLayoutManager(activity, 2)
        val coeff = getDpToPxCoefficient()
        B.filmsRecyclerVeiw.addItemDecoration(FilmListDecorator(2, 0, 8, 8, 16, coeff))
    }
    private val onClickListener: OnClickListener = object :View.OnClickListener {
        override fun onClick(btn: View){
            val radioButton = btn as RadioButton
            val newGenre = radioButton.text.toString()
            if (newGenre.equals(currentGenre)) {
                radioButton.isChecked = false
                currentGenre = null
            } else
                currentGenre = newGenre
            val newFilms = if (currentGenre == null) viewModel.getAllFilms()
                            else viewModel.filterFilmsByGenre(currentGenre as String)
            adapter.films = newFilms?.toMutableList()
        }
    }
}