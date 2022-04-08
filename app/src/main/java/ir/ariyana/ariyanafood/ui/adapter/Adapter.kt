package ir.ariyana.ariyanafood.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.ariyana.ariyanafood.databinding.FoodCardBinding
import ir.ariyana.ariyanafood.model.data.Item

class Adapter(private val data : ArrayList<Item>, private val itemEvents : ItemEvents) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(private val binding : FoodCardBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindData(position: Int) {
            binding.foodName.text = data[position].foodName
            binding.foodType.text = data[position].foodType
            binding.foodPrice.text = data[position].foodPrice + " VIP"
            binding.foodDistance.text = data[position].foodDistance + " Miles"
            binding.ratingBar.rating = data[position].ratingBar
            binding.numberOfRates.text = data[position].numberOfRates + " Ratings"
            Glide
                .with(binding.root.context)
                .load(data[position].foodImage)
                .into(binding.foodImage)

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
        val binding = FoodCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    // call this function from adapter to add one item
    fun addItem(newItem : Item) {
        data.add(data.size, newItem)
        notifyItemInserted(data.size)
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

    fun setData(itemsList : ArrayList<Item>){
        data.clear()
        data.addAll(itemsList)
        notifyDataSetChanged()
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