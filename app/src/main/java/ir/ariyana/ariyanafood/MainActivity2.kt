package ir.ariyana.ariyanafood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.ariyana.ariyanafood.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    lateinit var binding : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val foodName = binding.foodNameInput.editText?.text
        val foodType = binding.foodTypeInput.editText?.text
        val foodPrice = binding.foodPriceInput.editText?.text
        val foodDistance = binding.foodDistanceInput.editText?.text
        val foodImage = binding.foodImageInput.editText?.text

        binding.confirm.setOnClickListener {

        }
    }
}
