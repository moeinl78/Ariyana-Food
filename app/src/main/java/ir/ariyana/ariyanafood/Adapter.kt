package ir.ariyana.ariyanafood

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter(private val data : ArrayList<Item>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(itemView : View, private val context : Context) : RecyclerView.ViewHolder(itemView) {
        val foodImage = itemView.findViewById<ImageView>(R.id.foodImage)
        val foodName = itemView.findViewById<TextView>(R.id.foodName)
        val foodType = itemView.findViewById<TextView>(R.id.foodType)
        val foodPrice = itemView.findViewById<TextView>(R.id.foodPrice)
        val foodDistance = itemView.findViewById<TextView>(R.id.foodDistance)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
        val numberOfRates = itemView.findViewById<TextView>(R.id.numberOfRates)

        fun bindData(position: Int) {
            foodName.text = data[position].foodName
            foodType.text = data[position].foodType
            foodPrice.text = data[position].foodPrice + " VIP"
            foodDistance.text = data[position].foodDistance + " Miles"
            ratingBar.rating = data[position].ratingBar
            numberOfRates.text = data[position].numberOfRates + " Ratings"
            Glide
                .with(context)
                .load(data[position].foodImage)
                .into(foodImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_card, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}