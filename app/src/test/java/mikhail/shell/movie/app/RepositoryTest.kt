package mikhail.shell.movie.app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import mikhail.shell.movie.app.repositories.FilmRepository
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.koin.core.context.GlobalContext.get

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RepositoryTest {
    lateinit var repository: FilmRepository
    @Before
    fun initRepository()
    {
        //repository = get<FilmRepository>()
    }
    @Test
    fun testGettingAllFilms() = runBlocking {
        withContext(Dispatchers.IO) {
            val films = repository.getAll()
            assertNotNull(films)
        }
    }
}