package mikhail.shell.movie.app.api

import java.io.Serializable

data class ResponseResult<T>(val code: Int, val payload: T? = null) {
}