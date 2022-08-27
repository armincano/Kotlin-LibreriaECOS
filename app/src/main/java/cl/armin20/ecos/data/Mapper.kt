package cl.armin20.ecos.data

import cl.armin20.ecos.data.local.entities.BookLocal
import cl.armin20.ecos.data.local.entities.BooksListLocal
import cl.armin20.ecos.data.model.Book
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

/*fun fromBookToLocalEntity(wrapper: Book) : BookLocal {
    return wrapper.let{
        BookLocal(
            id = it.id,
            author = it.author,
            country = it.country,
            imageLink = it.imageLink,
            language = it.language,
            link = it.link,
            pages = it.pages,
            title = it.title,
            year = it.year,
            price = it.price,
            lastPrice = it.lastPrice,
            delivery = it.delivery,
        )
    }
}*/
