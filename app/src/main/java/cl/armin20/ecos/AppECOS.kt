package cl.armin20.ecos

import android.app.Application
import cl.armin20.ecos.data.BooksRepository
import cl.armin20.ecos.data.local.BooksDataBase

class AppECOS: Application() {

    private val dataBase by lazy {BooksDataBase.getDataBase(this)}
    val repository by lazy { BooksRepository(dataBase.getBooksDao()) }

}