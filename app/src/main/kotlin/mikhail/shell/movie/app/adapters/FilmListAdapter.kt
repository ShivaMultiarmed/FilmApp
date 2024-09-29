package mikhail.shell.movie.app.adapters

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.RecyclerView.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import mikhail.shell.movie.app.FilmCatalog
import mikhail.shell.movie.app.models.Film
import mikhail.shell.movie.app.views.FilmCardView

class FilmListAdapter(private val context: Context) : RecyclerView.Adapter<FilmListAdapter.FilmCardHolder>() {

    var films: List<Film>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmCardHolder {
        val filmCard = FilmCardView(context)
        filmCard.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        filmCard.elevation = 0.0f
        return FilmCardHolder(filmCard)
    }

    override fun onBindViewHolder(holder: FilmCardHolder, position: Int) {
        val film = films?.get(position) as Film
        holder.filmCard.setFilm(film)
        holder.filmCard.setOnClickListener{ card ->
            val filmCatalog = context as FilmCatalog
            filmCatalog.openFilm(film)
        }
    }

    override fun getItemCount() = if (films != null) films?.count() as Int else 0

    class FilmCardHolder(val filmCard: FilmCardView) : ViewHolder(filmCard)
}