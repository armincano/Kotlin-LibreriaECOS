package cl.armin20.ecos.data

import cl.armin20.ecos.data.local.entities.BooksListLocal
import cl.armin20.ecos.data.model.BooksList

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
