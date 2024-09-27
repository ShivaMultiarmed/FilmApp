package mikhail.shell.movie.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mikhail.shell.movie.app.databinding.FilmFragmentBinding
import mikhail.shell.movie.app.models.Film

class FilmFragment(private val film: Film) : Fragment() {
    private lateinit var B: FilmFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        B = FilmFragmentBinding.inflate(inflater)
        return B.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        B.setFilm(film)
    }
}