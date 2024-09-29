package mikhail.shell.movie.app.viewmodels

import androidx.lifecycle.ViewModel
import mikhail.shell.movie.app.models.Film
import mikhail.shell.movie.app.repositories.Repository

class FilmViewModel(private val repository: Repository<Film>): ViewModel() {
    private var films: MutableList<Film>? = null
    private var genres: MutableList<String>? = null
    suspend fun requestAllFilms() {
        if (films == null)
        {
            val responseResult = repository.getAll()!!
            if (responseResult.code in 200..299)
                films = responseResult.payload
        }
    }
    fun getGenres(): List<String>?
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
        return genres?.sortedWith {g1, g2 -> g1.compareTo(g2)}
    }
    fun filterFilmsByGenre(genre: String) = films
        ?.filter { film -> film.genres.contains(genre) }
        ?.sortedWith{ f1, f2 -> f2.name.compareTo(f1.name) }?.toList()
    fun getAllFilms() = films?.sortedWith { f1, f2 -> f1.localized_name.compareTo(f2.localized_name) }?.toList()
}