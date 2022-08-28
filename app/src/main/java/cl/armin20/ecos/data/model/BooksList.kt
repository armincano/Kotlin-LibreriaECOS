package cl.armin20.ecos.data.model

import cl.armin20.ecos.data.local.entities.BooksListLocal

data class BooksList(
    val id: Int,
    val author: String,
    val country: String,
    val imageLink: String,
    val language: String,
    val title: String
)

//mapper that receives a list
fun fromBooksListToLocalEntity(wrapper: List<BooksList>) : List<BooksListLocal> {
    return wrapper.map {
        BooksListLocal(
            id = it.id,
            author= it.author,
            country = it.country,
            imageLink = it.imageLink,
            language = it.language,
            title = it.title
        )
    }
}