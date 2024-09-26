package mikhail.shell.movie.app.api

import mikhail.shell.movie.app.models.Film
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface FilmApi {
    @GET("/sequeniatesttask/films.json")
    fun getAllFilms(): Call<Map<String, MutableList<Film>>>
}