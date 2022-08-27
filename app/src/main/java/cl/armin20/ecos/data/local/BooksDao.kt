package cl.armin20.ecos.data.local

import androidx.room.Dao
import androidx.room.Query
import cl.armin20.ecos.data.local.entities.BookLocal
import cl.armin20.ecos.data.local.entities.BooksListLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {
    @Query("SELECT * FROM books_list_table")
    fun getAllBooks(): Flow<List<BooksListLocal>>

    @Query("SELECT * FROM book_table WHERE id = :id")
    fun getSingleBook(id: Int): Flow<List<BookLocal>>
}