package ir.ariyana.ariyanafood.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.ariyana.ariyanafood.databinding.ActivityMainBinding
import ir.ariyana.ariyanafood.databinding.NewItemBinding
import ir.ariyana.ariyanafood.databinding.RemoveItemBinding
import ir.ariyana.ariyanafood.databinding.UpdateItemBinding
import ir.ariyana.ariyanafood.model.data.Item
import ir.ariyana.ariyanafood.model.room.AriyanaDB
import ir.ariyana.ariyanafood.model.room.ItemDao
import ir.ariyana.ariyanafood.presenters.main.MainContract
import ir.ariyana.ariyanafood.presenters.main.MainPresenter
import ir.ariyana.ariyanafood.ui.adapter.Adapter

class MainActivity : AppCompatActivity(), Adapter.ItemEvents, MainContract.View {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : Adapter
    private lateinit var itemDAO : ItemDao
    private lateinit var presenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // access database via itemDao
        itemDAO = AriyanaDB.createDataBase(this).itemDao
        presenter = MainPresenter(itemDAO)

        // save state of the program using sharedPreferences
        val sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE)
        if(sharedPreferences.getBoolean("app_first_run", true)){
            presenter.appFirstRun()
            sharedPreferences
                .edit()
                .putBoolean("app_first_run", false)
                .apply()
        }

        // add new item code is here ->
        binding.addItem.setOnClickListener {
            addNewItem()
        }

        // search for items code ->
        binding.searchTextInput.addTextChangedListener { inputText ->
            presenter.onItemSearch(inputText.toString())
        }
    }

    // add new item to database
    private fun addNewItem() {

        val dialog = AlertDialog.Builder(this).create()
        val view = NewItemBinding.inflate(layoutInflater)
        dialog.setView(view.root)
        dialog.setCancelable(true)
        dialog.show()

        view.confirm.setOnClickListener {
            val foodName = view.nameInput.text.toString()
            val foodType = view.typeInput.text.toString()
            val foodPrice = view.priceInput.text.toString()
            val foodDistance = view.distanceInput.text.toString()

            val images = arrayListOf(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4G8qi5-o-0jzyG4Rylf8D2fJAxjvM4JSRhg&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2egI-pAraQ5Ofzt4zSMW7Y6F1UCnDwsXjXg&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS24wvA9ozipJc5-IStQrqZIo_a3urpEZGIGA&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQavZS77mInKpbajzhaGj9JG6K4gJJt4ndKfw&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7WNX9WsTqIEZcsxSMnCZ_ufaHA5XlLWVhyQ&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7Wbvermj92gElygU8IU7_brGqqas0fkWacw&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwvcaANl_bB2oLTap3BrRoGw7H68_05tN1Dg&usqp=CAU",
            )
            val newImage = images.random()
            val rating = (1..5).random().toFloat()
            val rates = (1..1000).random()

            val newItem = Item(null, foodName, foodType, foodPrice, foodDistance, newImage, rating, "$rates")
            presenter.onAddButtonClicked(newItem)
            dialog.dismiss()
            binding.recycleMain.scrollToPosition(adapter.itemCount)
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
            val foodName = view.updateNameInput.text.toString()
            val foodType = view.updateTypeInput.text.toString()
            val foodPrice = view.updatePriceInput.text.toString()
            val foodDistance = view.updateDistanceInput.text.toString()

            val updatedItem = Item(item.id, foodName, foodType, foodPrice, foodDistance, item.foodImage, item.ratingBar, item.numberOfRates)
            presenter.onUpdateClicked(updatedItem, position)
            dialog.dismiss()
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
            presenter.onDeleteClicked(item, position)
        }

        view.removeDecline.setOnClickListener {
            dialog.dismiss()
        }
    }

    // NEW
    override fun showItems(items: List<Item>) {
        adapter = Adapter(ArrayList(items), this)
        binding.recycleMain.adapter = adapter
        binding.recycleMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun reloadItems(items: List<Item>) {
        adapter.setData(ArrayList(items))
    }

    override fun addItem(item: Item) {
        adapter.addItem(item)
    }

    override fun removeItem(item: Item, position: Int) {
        adapter.removeItem(item, position)
    }

    override fun updateItem(item: Item, position: Int) {
        adapter.updateItem(item, position)
    }
}