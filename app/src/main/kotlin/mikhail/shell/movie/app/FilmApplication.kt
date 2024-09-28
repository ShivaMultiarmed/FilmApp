package mikhail.shell.movie.app

import android.app.Application
import mikhail.shell.movie.app.modules.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FilmApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@FilmApplication)
            modules(mainModule)
        }
    }
}