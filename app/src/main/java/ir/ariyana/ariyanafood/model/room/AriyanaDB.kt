package ir.ariyana.ariyanafood.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.ariyana.ariyanafood.model.data.Item

// don't set exportSchema to true for security
@Database(version = 1, exportSchema = true, entities = [Item::class])
abstract class AriyanaDB : RoomDatabase(){

    // create dao value for each table (for this database it's only one)
    abstract val itemDao : ItemDao

    companion object {
        private var database : AriyanaDB? = null
        fun createDataBase(context : Context) : AriyanaDB {

            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    AriyanaDB::class.java,
                    "items.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return database!!
        }
    }
}