package ir.ariyana.ariyanafood.room

import androidx.room.*
import ir.ariyana.ariyanafood.Item

//data access object
@Dao
interface ItemDao {

    @Insert
    fun insertItem(item : Item)

    @Update
    fun updateItem(item : Item)

    @Delete
    fun removeItem(item : Item)

    @Query("SELECT * FROM table_food")
    fun receiveItems() : List<Item>

    @Query("SELECT * FROM table_food WHERE foodName LIKE '%'||:info||'%'")
    fun searchItem(info : String) : List<Item>
}