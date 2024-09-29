package mikhail.shell.movie.app.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.squareup.picasso.Picasso
import mikhail.shell.movie.app.R
import mikhail.shell.movie.app.databinding.FilmCardBinding
import mikhail.shell.movie.app.models.Film

class FilmCardView(context: Context?, attributeSet: AttributeSet?, onClickListener: OnClickListener? = null) : CardView(context as Context, attributeSet) {

    private val B: FilmCardBinding
    private val root: CardView

    init {
        val inflater = (context as Activity).layoutInflater
        B = FilmCardBinding.inflate(inflater, this, true)
        root = B.root as CardView
        root.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        if (onClickListener!= null)
            root.setOnClickListener(onClickListener)
        B.filmPoster.clipToOutline = true
    }

    constructor(context: Context?, onClickListener: OnClickListener? = null) : this (context, null, onClickListener)

    fun setFilm(film: Film) {
        B.film = film
        Picasso.get()
            .load(film.image_url)
            .error(R.drawable.error_img)
            .into(B.filmPoster)
    }
    fun getFilm() = B.film

}