package cl.armin20.ecos.data.remote

import cl.armin20.ecos.data.model.Book
import cl.armin20.ecos.data.model.BooksList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BooksApi {
    @GET("books/")
    suspend fun getBooksList(): Response<List<BooksList>>

    @GET("books_detail/{id}/")
    suspend fun getBook(@Path("id") id:Int): Response<Book>
}