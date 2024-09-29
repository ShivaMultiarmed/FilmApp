package mikhail.shell.movie.app.repositories

import mikhail.shell.movie.app.api.ResponseResult
import mikhail.shell.movie.app.models.Film
import java.io.Serializable

interface Repository<T : Serializable> {
    suspend fun getAll(): ResponseResult<MutableList<T>?>?
}