package cl.armin20.ecos.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cl.armin20.ecos.data.local.BooksDao
import cl.armin20.ecos.data.model.Book
import cl.armin20.ecos.data.model.BooksList
import cl.armin20.ecos.data.remote.BooksApi
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.google.gson.Gson
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BooksRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var booksDao: BooksDao
    private lateinit var booksApi: BooksApi
    // Subject under test
    private lateinit var booksRepository: BooksRepository
    private lateinit var mockWebServer: MockWebServer

    //Mockk
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        booksApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)

        booksDao = mockk<BooksDao>()
        booksRepository = BooksRepository(booksDao)
    }

    @Test
    fun `when getBooksList() is called and stored, should load data`() = runBlocking  {

        //Given
        val json = fakeJSON01ForMockWebServer()
        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(json)))

        //When the response from BooksApi is stored and successful
        val response = booksApi.getBooksList()

        //Then
        assertThat(response.body()).isNotNull()
        assertThat(response.body()!!.size).isEqualTo(2)
        assertThat(response.isSuccessful).isTrue()

    }

    @Test
    fun `when getBook(id) is called and stored, should load data with the specific id`()= runBlocking {

        //Given
        val json = fakeJSON02ForMockWebServer()
        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(json)))


        //When the response from BooksApi is stored and successful
        val response = booksApi.getBook(1)

        //Then
        assertThat(response.body()).isNotNull()
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isEqualTo(fakeJSON02ForMockWebServer())
        assertThat(response.body()?.id).isEqualTo(1)
    }

    companion object {
        fun fakeJSON01ForMockWebServer(): List<BooksList> {
            return listOf(BooksList(1,"Armin","chile","www.fake.com", "spanish","The Fake"),
                BooksList(2, "Lolo", "argentina", "www.nofake.arg","spanish","El Maradono"))
        }
        fun fakeJSON02ForMockWebServer(): Book {
            return Book(1,"Armin","chile","www.fake.com", "spanish","www.nofake.arg",123,"The Bog Book",1991,123456,120000,true)
        }
    }

//    suspend fun getBooksListFromRemote(){
//        val response = ecosBookRetrofitClient.getBooksList()
//        when(response.isSuccessful) {
//            true -> {
//                if (response.body().isNullOrEmpty()) {
//                    errorMessage.value = "ERROR IS NULL OR EMPTY"
//                } else {
//                    Log.d("faith", "repository getBooksListFromRemote, list size ${response.body()?.size}")
//                    booksDao.insertAllBooks(fromBooksListToLocalEntity(response.body()!!))
//                }
//            }
//            false -> {
//                errorMessage.value = response.message()
//            }
//        }
//    }

    /* suspend fun getBookFromRemote(id: Int){
        val response = ecosBookRetrofitClient.getBook(id)
        when(response.isSuccessful){
            true ->{
                if (response.body()==null) {
                    errorMessage.value = "ERROR IS NULL"
                } else {
                    val example = response.body()!!.toBookLocal()
                    booksDao.insertSingleBook(response.body()!!.toBookLocal())
                }
            }
            false -> {
                errorMessage.value = response.message()
            }
        }
    }*/


}