package mikhail.shell.movie.app.repositories


import mikhail.shell.movie.app.api.FilmApi
import mikhail.shell.movie.app.models.Film
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmRepository(private val api: FilmApi): Repository<Film> {
    override suspend fun getAll(): MutableList<Film>? {
        return try {
            val response = api.getAllFilms()
            if (response.isSuccessful) {
                val body = response.body()
                body?.get("films") as? MutableList<Film>
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}