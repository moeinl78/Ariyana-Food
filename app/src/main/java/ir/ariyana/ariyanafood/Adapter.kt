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

class Adapter(private val data : ArrayList<Item>, private val itemEvents : ItemEvents) : RecyclerView.Adapter<Adapter.ViewHolder>() {

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

            itemView.setOnClickListener {
                itemEvents.onItemClicked(data[adapterPosition], adapterPosition)
            }

            itemView.setOnLongClickListener {
                // send item and item position to the interface
                itemEvents.onItemLongClicked(data[adapterPosition], adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_card, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    // call this function from adapter to add one item
    fun addItem(newItem : Item) {
        data.add(0, newItem)
        notifyItemInserted(0)
    }

    // call this function from adapter to remove one item
    fun removeItem(item : Item, itemPosition : Int) {
        data.remove(item)
        notifyItemRemoved(itemPosition)
    }

    fun updateItem(item : Item, itemPosition: Int) {
        data[itemPosition] = item
        notifyItemChanged(itemPosition)
    }

    interface ItemEvents {
        // 1. create interface in adapter
        // 2. get an object of interface in args of adapter
        // 3. fill object of interface with your data
        // 4. implementation in MainActivity
        fun onItemClicked(item : Item, position: Int)
        fun onItemLongClicked(item : Item, position : Int)
    }
}