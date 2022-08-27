package cl.armin20.ecos.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import cl.armin20.ecos.data.local.BooksDao
import cl.armin20.ecos.data.local.entities.BookLocal
import cl.armin20.ecos.data.local.entities.BooksListLocal
import cl.armin20.ecos.data.remote.EcosBookRetrofitClient
import kotlinx.coroutines.flow.Flow

class BooksRepository (private val booksDao: BooksDao) {

    private val ecosBookRetrofitClient = EcosBookRetrofitClient.retrofitInstance()

    private val errorMessage = MutableLiveData<String>()

    fun getAllBooksFromDB(): Flow<List<BooksListLocal>> {
        return booksDao.getAllBooks()
    }

    fun getBookByIdFromDB(id: Int): Flow<BookLocal> {
        return booksDao.getSingleBook(id)
    }

    suspend fun getBooksListFromRemote(){
        val response = ecosBookRetrofitClient.getBooksList()
        when(response.isSuccessful) {
            true -> {
                if (response.body().isNullOrEmpty()) {
                    errorMessage.value = "ERROR IS NULL OR EMPTY"
                } else {
                    Log.d("faith", "repository getBooksListFromRemote, list size ${response.body()?.size}")
                    booksDao.insertAllBooks(fromBooksListToLocalEntity(response.body()!!))
                }
            }
            false -> {
                errorMessage.value = response.message()
            }
        }
    }

    suspend fun getBookFromRemote(id: Int){
        val response = ecosBookRetrofitClient.getBook(id)
        when(response.isSuccessful){
            true ->{
                if (response.body()==null) {
                    errorMessage.value = "ERROR IS NULL"
                } else {
                    booksDao.insertSingleBook(fromBookToLocalEntity(response.body()!!))
                    Log.d("faith2", "repository getBookFromRemote, title of the book ${response.body()?.title}")
                    Log.d("faith2", "repository getBookFromDB, title of the country ${getBookByIdFromDB(id).asLiveData().value?.country}")
                }
            }
            false -> {
                errorMessage.value = response.message()
            }
        }

    }



}