package mikhail.shell.movie.app.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import mikhail.shell.movie.app.R
import mikhail.shell.movie.app.models.Film

class FilmListFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.film_list_fragment, container, false)
    }
    fun addFilm(film: Film){
        Log.i("Film List Fragment", film.name)
    }
    fun createFilmCard(film: Film): View
    {
        val filmCard = layoutInflater.inflate(R.layout.film_card, null)
        val poster = filmCard.findViewById<ImageView>(R.id.film_poster)
        Picasso.with(activity)
            .load(film.image_url)
            .resizeDimen(poster.id,poster.id)
            .into(poster)
        val name = filmCard.findViewById<TextView>(R.id.film_name)
        name.text = film.name
        return filmCard
    }
}