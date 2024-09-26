package mikhail.shell.movie.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mikhail.shell.movie.app.models.Film
import mikhail.shell.movie.app.repositories.FilmRepository

class FilmViewModel: ViewModel() {
    private val repository = FilmRepository()
    private var films: MutableList<Film>? = null
    private var genres: MutableList<String>? = null
    suspend fun requestAllFilms() : List<Film>? {
        if (films == null)
            films = repository.getAllFilms()
        return films
    }
    fun fetchGenres(): List<String>?
    {
        if (genres == null)
        {
            genres = mutableListOf()
            films?.forEach {
                film -> film.genres.forEach { genre ->
                    if (genres?.contains(genre) == false)
                        genres?.add(genre)
                }
            }
        }
        return genres
    }
    fun filterFilmByGenre(genre: String) = films
        ?.filter { film -> film.genres.contains(genre) }
        ?.sortedWith{ f1, f2 -> f2.name.compareTo(f1.name) }
    fun getFilmById(id: Long) = films?.find { film -> film.id == id }
}