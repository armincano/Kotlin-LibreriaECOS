package cl.armin20.ecos.data

import androidx.lifecycle.MutableLiveData
import cl.armin20.ecos.data.local.BooksDao
import cl.armin20.ecos.data.remote.EcosBookRetrofitClient

class BooksRepository (private val booksDao: BooksDao) {

    private val ecosBookRetrofitClient = EcosBookRetrofitClient.retrofitInstance()

    private val errorMessage = MutableLiveData<String>()



}