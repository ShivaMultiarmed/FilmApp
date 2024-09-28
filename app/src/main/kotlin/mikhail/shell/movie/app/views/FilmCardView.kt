package mikhail.shell.movie.app.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import mikhail.shell.movie.app.R
import mikhail.shell.movie.app.databinding.FilmCardBinding
import mikhail.shell.movie.app.models.Film
import java.lang.Exception

class FilmCardView(context: Context?, attributeSet: AttributeSet?) : LinearLayout(context, attributeSet) {

    private val B: FilmCardBinding

    init {
        val inflater = (context as Activity).layoutInflater
        B = FilmCardBinding.inflate(inflater, this, true)
        (B.root as LinearLayout).layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    constructor(context: Context?) : this (context, null)

    fun setFilm(film: Film) {
        B.film = film
        Picasso.get()
            .load(film.image_url)
            .error(R.drawable.error_img)
            .into(B.filmPoster)
    }
    fun getFilm() = B.film

}