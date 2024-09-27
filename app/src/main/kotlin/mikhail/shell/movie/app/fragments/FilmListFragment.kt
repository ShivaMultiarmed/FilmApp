package mikhail.shell.movie.app.fragments

import mikhail.shell.movie.app.adapters.FilmListAdapter
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mikhail.shell.movie.app.FilmListDecorator
import mikhail.shell.movie.app.R
import mikhail.shell.movie.app.models.Film

class FilmListFragment: Fragment() {
    private var pendingFilms: List<Film>? = null
    private lateinit var adapter: FilmListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.film_list_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.films_recycler_veiw)
        adapter = FilmListAdapter(activity as Activity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        val coeff = getDpToPxCoefficient()
        recyclerView.addItemDecoration(FilmListDecorator(2, 0, 8, 8, 16, coeff))
        pendingFilms?.let {
            adapter.films = it
            pendingFilms = null
        }
    }
    fun setFilms(films: List<Film>?)
    {
        if (::adapter.isInitialized) {
            adapter.films = films
        } else {
            pendingFilms = films
        }
    }
    private fun getDpToPxCoefficient() = context?.resources?.displayMetrics?.density?.toDouble() as Double
}