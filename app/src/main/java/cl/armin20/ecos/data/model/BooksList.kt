package cl.armin20.ecos.data.model

import androidx.lifecycle.Transformations.map
import cl.armin20.ecos.data.local.entities.BooksListLocal

data class BooksList(
    val id: Int,
    val author: String,
    val country: String,
    val imageLink: String,
    val language: String,
    val title: String
)