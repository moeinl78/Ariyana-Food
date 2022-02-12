package ir.ariyana.ariyanafood

import android.text.Editable

data class Item(
    val foodName: String,
    val foodType: String,
    val foodPrice: String,
    val foodDistance: String,
    val foodImage: String,
    val ratingBar: Float,
    val numberOfRates: String
)