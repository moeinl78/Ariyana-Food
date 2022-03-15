package ir.ariyana.ariyanafood

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.ariyana.ariyanafood.databinding.ActivityMainBinding
import ir.ariyana.ariyanafood.databinding.NewItemBinding
import ir.ariyana.ariyanafood.databinding.RemoveItemBinding
import ir.ariyana.ariyanafood.databinding.UpdateItemBinding
import ir.ariyana.ariyanafood.room.AriyanaDB
import ir.ariyana.ariyanafood.room.ItemDao

class MainActivity : AppCompatActivity(), Adapter.ItemEvents {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : Adapter
    private lateinit var itemDAO : ItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // save state of the program using sharedPreferences
        val sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE)
        if(sharedPreferences.getBoolean("app_first_run", true)){
            appFirstRun()
            sharedPreferences
                .edit()
                .putBoolean("app_first_run", false)
                .apply()
        }

        // access database via itemDao
        itemDAO = AriyanaDB.createDataBase(this).itemDao

        // call receiveItems function
        receiveItems()

        // add new item code is here ->
        binding.addItem.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val view = NewItemBinding.inflate(layoutInflater)
            dialog.setView(view.root)
            dialog.setCancelable(true)
            dialog.show()
        }

        // search for items code ->
        binding.searchTextInput.addTextChangedListener { inputText ->
            if(inputText!!.isNotEmpty()) {
                // filter data
            }
            else {

            }
        }
    }

    // read all data from database
    private fun receiveItems() {

        val itemList = itemDAO.receiveItems()

        adapter = Adapter(itemList.clone() as ArrayList<Item>, this)
        binding.recycleMain.adapter = adapter
        binding.recycleMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    // check if it's first time program is running
    private fun appFirstRun() {
        // add static data for first run if you want!
        // using map or create a method to add array list of items into db
        val data : ArrayList<Item> = arrayListOf(
            Item(1, "Hamburger", "US Fast Food", "17.5", "1000", "https://upload.wikimedia.org/wikipedia/commons/4/47/Hamburger_%28black_bg%29.jpg", 4.5f, "930"),
            Item(2, "Grilled Fish", "East Asia", "28", "932", "https://static.toiimg.com/photo/52669221.cms", 3.5f, "1450"),
            Item(3, "Lasagna", "Italian", "24", "430", "https://cafedelites.com/wp-content/uploads/2018/01/Mamas-Best-Lasagna-IMAGE-43.jpg", 4f, "2045"),
            Item(4, "Pizza", "Italian", "70", "430", "https://upload.wikimedia.org/wikipedia/commons/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg", 3f, "1025"),
            Item(5, "Sushi", "Japanese", "110", "980", "https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg?20201002142956&q=80&rw=750&rh=536", 1f, "6487"),
            Item(6, "Roasted Fish", "Iranian", "17.5", "5", "https://static01.nyt.com/images/2021/09/20/dining/nd-mahi/nd-mahi-articleLarge.jpg", 2f, "7800"),
            Item(7, "Fried Chicken", "Iranian", "17.5", "5", "https://static.toiimg.com/thumb/61589069.cms?width=1200&height=900", 5f, "2"),
        )
        data.map { item ->
            itemDAO.insertItem(item)
        }
    }

    // update item code ->
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

        }
    }

    // delete item code ->
    override fun onItemLongClicked(item : Item, position : Int) {
        val dialog = AlertDialog.Builder(this).create()
        val view = RemoveItemBinding.inflate(layoutInflater)
        dialog.setView(view.root)
        dialog.setCancelable(true)
        dialog.show()

        view.removeAccept.setOnClickListener {
            dialog.dismiss()
            adapter.removeItem(item, position)
            itemDAO.removeItem(item)
        }

        view.removeDecline.setOnClickListener {
            dialog.dismiss()
        }
    }
}