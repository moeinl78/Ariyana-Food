package ir.ariyana.ariyanafood.presenters.main

import ir.ariyana.ariyanafood.model.data.Item

interface MainContract {

    // add all events here
    interface Presenter {
        // base of all presenters
        fun onAttach(view : MainContract.View)
        fun onDestroy()

        fun appFirstRun()
        fun onItemSearch(query : String)
        fun onUpdateClicked(item : Item, position : Int)
        fun onDeleteClicked(item : Item, position : Int)
        fun onAddButtonClicked(item : Item)
    }

    interface View {

        fun showItems(items : List<Item>)
        fun reloadItems(items : List<Item>)
        fun addItem(item : Item)
        fun removeItem(item : Item, position: Int)
        fun updateItem(item : Item, position: Int)
    }
}