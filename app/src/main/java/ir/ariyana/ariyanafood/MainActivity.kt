package ir.ariyana.ariyanafood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.ariyana.ariyanafood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

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

        val adapter = Adapter(itemList)
        binding.recycleMain.adapter = adapter
        binding.recycleMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}