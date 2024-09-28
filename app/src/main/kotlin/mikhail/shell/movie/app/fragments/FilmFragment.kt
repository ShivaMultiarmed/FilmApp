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
import mikhail.shell.movie.app.databinding.FilmFragmentBinding
import mikhail.shell.movie.app.models.Film
import java.math.RoundingMode
import java.math.RoundingMode.HALF_UP

class FilmFragment(private val film: Film) : Fragment() {
    private lateinit var B : FilmFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        B = FilmFragmentBinding.inflate(inflater)
        return B.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        B.film = film
        B.genresAndYear.text = createGenresAndYearText()
        B.rating.text = film.rating.round().toString()
        Picasso.get()
            .load(film.image_url)
            .into(B.oneFilmPoster)
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