package ir.ariyana.ariyanafood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.ariyana.ariyanafood.databinding.ActivityMainBinding
import ir.ariyana.ariyanafood.databinding.NewItemBinding
import ir.ariyana.ariyanafood.databinding.RemoveItemBinding
import ir.ariyana.ariyanafood.databinding.UpdateItemBinding

class MainActivity : AppCompatActivity(), Adapter.ItemEvents {

    lateinit var binding : ActivityMainBinding
    lateinit var adapter : Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemList = arrayListOf(
            Item("Pizza", "Persian Pizz", "$32 VIP", "32", "https://lh3.googleusercontent.com/p/AF1QipNlkgfsC62K3EYU3b-zsG7OTASXXbni9pVBV7ZI=s1600-w400", 4.5f, "110"),
            Item("Hamburger", "US Hamburger", "$17", "81", "https://static.toiimg.com/thumb/79693966.cms?width=680&height=512&imgsize=150513", 4f, "181"),
            Item("Sushi", "Chinese", "$60", "200", "https://img.static-rmg.be/a/view/q75/w960/h520/2163026/super-sushi-2-960x520.jpg", 3.5f, "254"),
            Item("Kebab", "Turkish", "$90", "15", "https://www.okokorecepten.nl/i/recepten/kookboeken/2015/echte-manen-dieet-2/doner-kebab-light-500.jpg", 5f, "1500"),
        )

        adapter = Adapter(itemList.clone() as ArrayList<Item>, this)
        binding.recycleMain.adapter = adapter
        binding.recycleMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.addItem.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val view = NewItemBinding.inflate(layoutInflater)
            dialog.setView(view.root)
            dialog.setCancelable(true)
            dialog.show()

            view.confirm.setOnClickListener {
                if (view.nameInput.length() > 0 && view.typeInput.length() > 0 && view.priceInput.length() > 0 && view.distanceInput.length() > 0) {
                    val foodName = view.nameInput.text.toString()
                    val foodType = view.typeInput.text.toString()
                    val foodPrice = view.priceInput.text.toString()
                    val foodDistance = view.distanceInput.text.toString()
                    val numOfRates = (1..1000).random().toString()
                    val ratingBar = (1..5).random().toFloat()

                    val randomURL = (0..3).random()
                    val url = itemList[randomURL].foodImage

                    val item = Item(foodName, foodType, foodPrice, foodDistance, url, ratingBar, numOfRates)
                    adapter.addItem(item)
                    binding.recycleMain.scrollToPosition(0)
                    dialog.dismiss()
                }
                else {
                    Toast.makeText(this, "please make sure to fill all inputs!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemClicked(item: Item, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val view = UpdateItemBinding.inflate(layoutInflater)
        dialog.setView(view.root)
        dialog.setCancelable(true)
        dialog.show()

        view.updateNameInput.setText(item.foodName)
        view.updateTypeInput.setText(item.foodType)
        view.updatePriceInput.setText(item.foodPrice)
        view.updateDistanceInput.setText(item.foodDistance)

        view.updateConfirm.setOnClickListener {
            if (view.updateNameInput.length() > 0 && view.updateTypeInput.length() > 0 && view.updatePriceInput.length() > 0 && view.updateDistanceInput.length() > 0){

                val foodName = view.updateNameInput.text.toString()
                val foodType = view.updateTypeInput.text.toString()
                val foodPrice = view.updatePriceInput.text.toString()
                val foodDistance = view.updateDistanceInput.text.toString()

                val updatedItem = Item(foodName, foodType, foodPrice, foodDistance, item.foodImage, item.ratingBar, item.numberOfRates)

                adapter.updateItem(updatedItem, position)
                dialog.dismiss()
            }
            else {
                Toast.makeText(this, "please enter valid data!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemLongClicked(item : Item, position : Int) {
        val dialog = AlertDialog.Builder(this).create()
        val view = RemoveItemBinding.inflate(layoutInflater)
        dialog.setView(view.root)
        dialog.setCancelable(true)
        dialog.show()

        view.removeAccept.setOnClickListener {
            dialog.dismiss()
            adapter.removeItem(item, position)
        }

        view.removeDecline.setOnClickListener {
            dialog.dismiss()
        }
    }
}