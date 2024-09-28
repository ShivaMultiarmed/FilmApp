package mikhail.shell.movie.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import mikhail.shell.movie.app.R
import mikhail.shell.movie.app.models.Film
import java.math.RoundingMode
import java.math.RoundingMode.HALF_UP

class FilmFragment(private val film: Film) : Fragment() {
    private lateinit var poster: ImageView
    private lateinit var localizedName: TextView
    private lateinit var genresAndYear: TextView
    private lateinit var rating: TextView
    private lateinit var description: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.film_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        poster = view.findViewById(R.id.one_film_poster)
        localizedName = view.findViewById(R.id.localized_name)
        genresAndYear = view.findViewById(R.id.genres_and_year)
        rating = view.findViewById(R.id.rating)
        description = view.findViewById(R.id.description)

        localizedName.text = film.localized_name
        genresAndYear.text = createGenresAndYearText()
        rating.text = film.rating.round().toString()
        description.text = film.description
        Picasso.with(activity)
            .load(film.image_url)
            .into(poster)
    }
    private fun createGenresAndYearText(): String
    {
        val builder = StringBuilder()
        film.genres.forEach { genre ->
            builder.append("$genre, ")
        }
        builder.append("${film.year} год")
        return builder.toString()
    }
    private fun Double.round() = this.toBigDecimal().setScale(1, HALF_UP).toDouble()
}