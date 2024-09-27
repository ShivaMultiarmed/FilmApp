package mikhail.shell.movie.app.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.RecyclerView.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import mikhail.shell.movie.app.R
import mikhail.shell.movie.app.models.Film
import mikhail.shell.movie.app.views.FilmCardView

class FilmListAdapter(private val activity: Activity, private val onClickListener: View.OnClickListener) : RecyclerView.Adapter<FilmListAdapter.FilmCardHolder>() {

    var films: List<Film>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmCardHolder {
        val filmCard = FilmCardView(activity)
        filmCard.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return FilmCardHolder(filmCard)
    }

    override fun onBindViewHolder(holder: FilmCardHolder, position: Int) {
        val film = films?.get(position)
        val filmCard = holder.filmCard.setFilm(film as Film)
        holder.filmCard.setOnClickListener(onClickListener)
    }

    override fun getItemCount() = films?.count() as Int

    class FilmCardHolder(val filmCard: FilmCardView) : ViewHolder(filmCard)
}