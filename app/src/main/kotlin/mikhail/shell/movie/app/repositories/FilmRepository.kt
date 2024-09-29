package mikhail.shell.movie.app.repositories


import mikhail.shell.movie.app.api.FilmApi
import mikhail.shell.movie.app.api.ResponseResult
import mikhail.shell.movie.app.models.Film

class FilmRepository(private val api: FilmApi): Repository<Film> {
    override suspend fun getAll(): ResponseResult<MutableList<Film>?>? {
        return try {
            val response = api.getAllFilms()
            if (response.isSuccessful) {
                val body = response.body()?.get("films") as MutableList<Film>
                ResponseResult(response.code(), body)
            } else {
                ResponseResult(response.code())
            }
        } catch (e: Exception) {
            null
        }
    }
}