package cl.armin20.ecos.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.screenshot.Screenshot.capture
import cl.armin20.ecos.data.BooksRepository
import cl.armin20.ecos.data.local.BooksDataBaseTest.Companion.fakeBooksListLocal
import cl.armin20.ecos.data.local.entities.BookLocal
import cl.armin20.ecos.data.local.entities.BooksListLocal
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert
import org.junit.runner.RunWith

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
        booksBookViewModel = BooksBookViewModel(fakeRepository)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun givenMock() = runBlocking{

        // given

        coEvery { fakeRepository.getAllBooksFromDB()} returns flowOf(listOf<BooksListLocal>(
            BooksListLocal(1,"Armin","chile","www.fake.com", "spanish","The Fake"),
            BooksListLocal(2, "Lolo", "argentina", "www.nofake.arg","spanish","El Maradono")
        ))


        // when
       /* val example = booksBookViewModel.booksFromRepository.observeForever {

            assertThat(it).isEqualTo(5)
        }*/

        val example = fakeRepository.getAllBooksFromDB().asLiveData()


//         then
        assertThat(example.value?.size).isEqualTo(3)


    }

    companion object {
        fun fakeBooksListLocal(): Flow<List<BooksListLocal>> {

            val flow = flowOf(listOf<BooksListLocal>(
                BooksListLocal(1,"Armin","chile","www.fake.com", "spanish","The Fake"),
                BooksListLocal(2, "Lolo", "argentina", "www.nofake.arg","spanish","El Maradono")
            ))


            return flow

        }


        fun fakeBookLocal(): BookLocal {
            return BookLocal(1,"Armin","chile","www.fake.com", "spanish","www.nofake.arg",123,"The Bog Book",1991,123456,120000,true)
        }
    }
}