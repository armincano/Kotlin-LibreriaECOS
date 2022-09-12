package cl.armin20.ecos.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.armin20.ecos.data.BooksRepository
import cl.armin20.ecos.data.local.entities.BookLocal
import cl.armin20.ecos.data.local.entities.BooksListLocal
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class BooksBookViewModelTest {

    @RelaxedMockK
    private lateinit var fakeRepository: BooksRepository

    private lateinit var booksBookViewModel: BooksBookViewModel

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun verifySelectedItemSetFromAdapter() = runBlocking {

        //given
        every { fakeRepository.getAllBooksFromDB() } returns flow {
            emit(
                listOf(
                    BooksListLocal(1,"Armin","chile","www.fake.com", "spanish","The Fake"),
                    BooksListLocal(2, "Lolo", "argentina", "www.nofake.arg","spanish","El Maradono")
                )
            )
        }

        booksBookViewModel = BooksBookViewModel(fakeRepository)

        //when
        booksBookViewModel.getSelectItem(BooksListLocal(1,"Armin","chile","www.fake.com", "spanish","The Fake"))

        //then
        booksBookViewModel.selectedItem.observeForever {
            assertThat(it.id).isEqualTo(1)
        }
    }

    @Test
    fun verifyCurrentBook2GivingId() = runBlocking {
        //given
        every { fakeRepository.getAllBooksFromDB() } returns flow {
            emit(
                listOf(
                    BooksListLocal(1,"Armin","chile","www.fake.com", "spanish","The Fake"),
                    BooksListLocal(2, "Lolo", "argentina", "www.nofake.arg","spanish","El Maradono")
                )
            )
        }

        every { fakeRepository.getBookByIdFromDB(1)} returns flow {
            emit(BookLocal(1,"Armin","chile","www.fake.com", "spanish","www.nofake.arg",123,"The Bog Book",1991,123456,120000,true))
        }

        booksBookViewModel = BooksBookViewModel(fakeRepository)

        //when
        booksBookViewModel.getSelectItem(BooksListLocal(1,"Armin","chile","www.fake.com", "spanish","The Fake"))

        booksBookViewModel.getBookByIdFromRemoteToLocal()

        //then
        // I need a delay so the assertion has time to work or is it a thread issue?
        delay(1000)
        booksBookViewModel.currentBook2.observeForever {
            assertThat(it.id).isEqualTo(1)
        }

    }

}