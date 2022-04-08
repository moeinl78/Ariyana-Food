package ir.ariyana.ariyanafood.presenters.main

import ir.ariyana.ariyanafood.model.data.Item
import ir.ariyana.ariyanafood.model.room.ItemDao


// MainActivity < - > MainPresenter
class MainPresenter(private val itemDao: ItemDao) : MainContract.Presenter {

    private var viewLayer : MainContract.View? = null

    override fun onAttach(view: MainContract.View) {
        this.viewLayer = view

        val items = itemDao.receiveItems()
        viewLayer!!.showItems(items)
    }

    override fun onDestroy() {
        viewLayer = null
    }

    override fun appFirstRun() {

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
            itemDao.insertItem(item)
        }
    }

    override fun onItemSearch(query: String) {

        if(query.isNotEmpty()) {
            val queryResult = itemDao.searchItem(query)
            viewLayer!!.reloadItems(queryResult)
        }
        else {
            val data = itemDao.receiveItems()
            viewLayer!!.showItems(data)
        }
    }

    override fun onUpdateClicked(item: Item, position: Int) {
        // model
        itemDao.updateItem(item)
        // view
        viewLayer!!.updateItem(item, position)
    }

    override fun onDeleteClicked(item: Item, position: Int) {
        itemDao.removeItem(item)
        viewLayer!!.removeItem(item, position)
    }

    override fun onAddButtonClicked(item : Item) {
        itemDao.insertItem(item)
        viewLayer!!.addItem(item)
    }
}