package mikhail.shell.movie.app.modules

import com.squareup.picasso.Downloader
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import mikhail.shell.movie.app.api.FilmApi
import mikhail.shell.movie.app.models.Film
import mikhail.shell.movie.app.repositories.FilmRepository
import mikhail.shell.movie.app.repositories.Repository
import mikhail.shell.movie.app.viewmodels.FilmViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

val mainModule = module {
    val BASE_URL = "https://s3-eu-west-1.amazonaws.com"
    single {
        OkHttpClient.Builder()
            .connectTimeout(7, SECONDS)
            .readTimeout(7, SECONDS)
            .writeTimeout(7, SECONDS)
            .build()
    }
    single<Downloader> {
        OkHttp3Downloader(get<OkHttpClient>())
    }
    single<Converter.Factory> {
        GsonConverterFactory.create()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
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