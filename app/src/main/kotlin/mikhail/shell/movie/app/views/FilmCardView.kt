package mikhail.shell.movie.app.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import mikhail.shell.movie.app.databinding.FilmCardBinding
import mikhail.shell.movie.app.models.Film

class FilmCardView(context: Context?, attributeSet: AttributeSet?) : LinearLayout(context, attributeSet) {

    private val B: FilmCardBinding

    init {
        val inflater = (context as Activity).layoutInflater
        B = FilmCardBinding.inflate(inflater, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 270)
    }

    constructor(context: Context?) : this (context, null)

    fun setFilm(film: Film) {
        B.film = film
        Picasso.with(context)
            .load(film.image_url)
            .into(B.filmPoster)
    }
    fun getFilm() = B.film

}