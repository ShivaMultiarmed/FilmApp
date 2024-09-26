package adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import mikhail.shell.movie.app.R
import mikhail.shell.movie.app.models.Film

class FilmListAdapter(val activity: Activity) : RecyclerView.Adapter<FilmListAdapter.FilmCardHolder>() {

    var films: List<Film>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmCardHolder {
        val filmCard = activity.layoutInflater.inflate(R.layout.film_card, null)
        return FilmCardHolder(filmCard)
    }

    override fun onBindViewHolder(holder: FilmCardHolder, position: Int) {
        val film = films?.get(position)
        val filmCard = holder.filmCard
        val poster = filmCard.findViewById<ImageView>(R.id.film_poster)
        Picasso.with(activity)
            .load(film?.image_url)
            //.resizeDimen(300,300)
            .into(poster)
        val name = filmCard.findViewById<TextView>(R.id.film_name)
        name.text = film?.name
    }

    override fun getItemCount() = films?.count() as Int

    class FilmCardHolder(val filmCard: View) : ViewHolder(filmCard)
}