package cl.armin20.ecos.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import cl.armin20.ecos.data.BooksRepository
import cl.armin20.ecos.data.local.entities.BookLocal
import cl.armin20.ecos.data.local.entities.BooksListLocal
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BooksBookViewModel(private val repository: BooksRepository) : ViewModel() {

    private val _selectedItem = MutableLiveData<BooksListLocal>()
    val selectedItem: LiveData<BooksListLocal>
    get() = _selectedItem

    private val _currentBook2 = MutableLiveData<BookLocal>()
    val currentBook2: LiveData<BookLocal>
    get() = _currentBook2

    init {
        getBooksFromRemoteToLocal()
    }

    val booksFromRepository: LiveData<List<BooksListLocal>> = repository.getAllBooksFromDB().asLiveData()

    private fun getBooksFromRemoteToLocal() = viewModelScope.launch {
        repository.getBooksListFromRemote()
    }

    fun getBookByIdFromRemoteToLocal() = viewModelScope.launch {
        repository.getBookFromRemote(selectedItem.value!!.id)
        Log.d("faith3", "currentBook value ${repository.getBookByIdFromDB(selectedItem.value!!.id).first()}")
        _currentBook2.value = (repository.getBookByIdFromDB(selectedItem.value!!.id).first())
        Log.d("faith3", "currentBook2 value ${_currentBook2.value}")
    }

    fun getSelectItem(item: BooksListLocal) {
        _selectedItem.value = item
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