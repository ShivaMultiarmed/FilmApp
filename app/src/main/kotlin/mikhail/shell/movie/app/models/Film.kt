package mikhail.shell.movie.app.models

import java.io.Serializable

data class Film(val id: Long,
                val localized_name: String,
                val name: String,
                val year: Short,
                val rating: Double,
                val image_url: String,
                val description: String,
                val genres: List<String>
                ) : Serializable
