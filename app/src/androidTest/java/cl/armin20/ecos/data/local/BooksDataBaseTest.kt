package cl.armin20.ecos.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.armin20.ecos.data.local.entities.BookLocal
import cl.armin20.ecos.data.local.entities.BooksListLocal
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class BooksDataBaseTest{

    private lateinit var booksDao: BooksDao
    private lateinit var db: BooksDataBase

    //swaps the background executor used by the Architecture Components
    // with a different one which executes each task synchronously.
    // Makes each task executed one after another, prevents asynchronous operations,
    // so we can make sure our LiveData objects don't call the android main thread.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BooksDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        booksDao = db.getBooksDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun verifyInsertEmptyBooksListData() = runBlocking {
        //Given
        val emptyBooksList = listOf<BooksListLocal>()
        //when
        booksDao.insertAllBooks(fakeBooksListLocal())
        //then
        val sample01 = booksDao.getAllBooks().first()
        delay(3000)//3 secs to debounce user clicks, or make sure you refresh data after a certain amount of time.
        assertThat(sample01.size).isEqualTo(2)
        assertThat(sample01[1].id).isEqualTo(2)
    }

    @Test
    fun verifyInsertFakeBookData() = runBlocking {
        //Given
        //when
        booksDao.insertSingleBook(fakeBookLocal())
        //then
        val sample02 = booksDao.getSingleBook(1)

        assertThat(sample02).isNotNull()
        assertThat(sample02.first()).isEqualTo(fakeBookLocal())
        assertThat(sample02.first().id).isEqualTo(1)
    }



    companion object {
        fun fakeBooksListLocal(): List<BooksListLocal> {
            return listOf(
                BooksListLocal(1,"Armin","chile","www.fake.com", "spanish","The Fake"),
                BooksListLocal(2, "Lolo", "argentina", "www.nofake.arg","spanish","El Maradono")
            )
        }
        fun fakeBookLocal(): BookLocal {
            return BookLocal(1,"Armin","chile","www.fake.com", "spanish","www.nofake.arg",123,"The Bog Book",1991,123456,120000,true)
        }
    }


}