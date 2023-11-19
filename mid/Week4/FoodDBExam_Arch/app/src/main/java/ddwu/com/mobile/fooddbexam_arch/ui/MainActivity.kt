package ddwu.com.mobile.fooddbexam_arch.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.fooddbexam_arch.FoodApplication
import ddwu.com.mobile.fooddbexam_arch.data.Food
import ddwu.com.mobile.fooddbexam_arch.databinding.ActivityMainBinding
import ddwu.com.mobile.fooddbexam_arch.ui.FoodAdapter

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    lateinit var mainBinding : ActivityMainBinding
    lateinit var adapter : FoodAdapter

    val viewModel : FoodViewModel by viewModels {
        FoodViewModelFactory((application as FoodApplication).repository)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        Log.d(TAG, "onCreate!!!")

        adapter = FoodAdapter()
        mainBinding.rvFoods.adapter = adapter
        mainBinding.rvFoods.layoutManager = LinearLayoutManager(this)

        viewModel.allFoods.observe(this, {foods ->
            adapter.foods = foods
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Observing!!!")
        })

        mainBinding.btnAdd.setOnClickListener {
            viewModel.addFood(
                Food(0, mainBinding.etFood.text.toString(),
                    mainBinding.etCountry.text.toString())
            )
        }
        mainBinding.btnModify.setOnClickListener {  }
        mainBinding.btnRemove.setOnClickListener {
            val food = mainBinding.etCountry.text.toString()
            viewModel.deleteFood(food)
        }
        mainBinding.btnShow.setOnClickListener {

        }
    }
}