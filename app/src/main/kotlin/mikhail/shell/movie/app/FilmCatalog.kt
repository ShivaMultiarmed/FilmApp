package mikhail.shell.movie.app

import mikhail.shell.movie.app.models.Film

interface FilmCatalog {
    fun openFilm(film: Film)
}