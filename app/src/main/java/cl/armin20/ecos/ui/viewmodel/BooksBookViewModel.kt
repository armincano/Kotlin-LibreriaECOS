package cl.armin20.ecos.ui.viewmodel

import androidx.lifecycle.*
import cl.armin20.ecos.data.BooksRepository
import cl.armin20.ecos.data.local.entities.BooksListLocal
import kotlinx.coroutines.launch

class BooksBookViewModel(private val repository: BooksRepository) : ViewModel() {

    init {
        getBooksFromRemoteToLocal()
    }

    val booksFromRepository: LiveData<List<BooksListLocal>> = repository.getAllBooksFromDB().asLiveData()

    //TODO getBookByIdFromRepository and remote


    private fun getBooksFromRemoteToLocal() = viewModelScope.launch {
        repository.getBooksListFromRemote()
    }
}

class BooksBookViewModelFactory(private val repository: BooksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooksBookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BooksBookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}