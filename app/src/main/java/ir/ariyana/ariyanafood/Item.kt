package ir.ariyana.ariyanafood

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_food")
data class Item(
    // generate unique id's for each item in database
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,

    // you can change each column name using @ColumnInfo
    val foodName : String,
    val foodType : String,
    val foodPrice : String,
    val foodDistance : String,
    val foodImage : String,
    val ratingBar : Float,
    val numberOfRates : String
)