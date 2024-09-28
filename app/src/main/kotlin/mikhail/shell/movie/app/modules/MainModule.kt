package mikhail.shell.movie.app.modules

import mikhail.shell.movie.app.api.FilmApi
import mikhail.shell.movie.app.models.Film
import mikhail.shell.movie.app.repositories.FilmRepository
import mikhail.shell.movie.app.repositories.Repository
import mikhail.shell.movie.app.viewmodels.FilmViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {
    single {
        OkHttpClient()
    }
    single<Converter.Factory> {
        GsonConverterFactory.create()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://s3-eu-west-1.amazonaws.com")
            .client(get())
            .addConverterFactory(get())
            .build()
    }
    single {
        get<Retrofit>()
            .create(FilmApi::class.java)
    }
    single<Repository<Film>> {
        FilmRepository(get())
    }
    viewModel {
        FilmViewModel(get())
    }
}