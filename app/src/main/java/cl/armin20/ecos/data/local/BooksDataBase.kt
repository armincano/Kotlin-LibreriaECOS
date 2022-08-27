package cl.armin20.ecos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.armin20.ecos.data.local.entities.BookLocal
import cl.armin20.ecos.data.local.entities.BooksListLocal

@Database(
    entities = [BooksListLocal::class, BookLocal::class],
    version = 1,
    exportSchema = false
)
abstract class BooksDataBase: RoomDatabase() {

    abstract fun getBooksDao(): BooksDao

    companion object {
        @Volatile
        private var INSTANCE: BooksDataBase? = null

        fun getDataBase(context: Context): BooksDataBase {
            return INSTANCE ?: synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    BooksDataBase::class.java,
                    "BooksDB"
                ).build()
                INSTANCE  = tempInstance
                return tempInstance
            }
        }
    }
}