package mikhail.shell.movie.app.repositories


import mikhail.shell.movie.app.api.FilmApi
import mikhail.shell.movie.app.models.Film
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class FilmRepository {
    private val BASE_URL = "https://s3-eu-west-1.amazonaws.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(FilmApi::class.java)
    suspend fun getAllFilms(): MutableList<Film>? {
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