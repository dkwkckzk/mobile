package ddwu.com.mobile.roomexam01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ddwu.com.mobile.roomexam01.data.Food
import ddwu.com.mobile.roomexam01.data.FoodDao
import ddwu.com.mobile.roomexam01.data.FoodDatabase
import ddwu.com.mobile.roomexam01.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var binding: ActivityMainBinding
   // lateinit var foodAdapter: FoodAdapter

    lateinit var db : FoodDatabase // DB 인스턴스
    lateinit var foodDao : FoodDao // DAO 인스턴스

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FoodDatabase.getDatabase(this)
        foodDao = db.foodDao()

        /*RecyclerView 의 layoutManager 지정
        binding.foodRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }*/

        /*샘플 데이터, DB 사용 시 DB에서 읽어온 데이터로 대체 필요*/
        val foods = ArrayList<Food>()
        foods.add(Food(1,"된장찌개", "한국"))
        foods.add(Food(2,"김치찌개", "한국"))
        foods.add(Food(3,"마라탕", "중국"))
        foods.add(Food(4,"훠궈", "중국"))
        foods.add(Food(5,"스시", "일본"))
        foods.add(Food(6,"오코노미야키", "일본"))

        /*foodAdapter = FoodAdapter(foods)

        foodAdapter 에 LongClickListener 구현 및 설정
        val onLongClickListener = object: FoodAdapter.OnItemLongClickListener {
            override fun onItemLongClickListener(view: View, pos: Int) {
                Log.d(TAG, "Long Click!! $pos")
            }
        }
        foodAdapter.setOnItemLongClickListener(onLongClickListener)

        binding.foodRecyclerView.adapter = foodAdapter*/

        CoroutineScope(IO).launch {
            foods.forEach { food ->
                foodDao.insertFood(food)
            }
            showAllFoods()
        }


        binding.btnShow.setOnClickListener{
            val country = binding.etNation.text.toString()
            showFoodByCountry(country)
        } // 입력한 나라만 보이도록

        binding.btnInsert.setOnClickListener{
            val food2 = binding.etFood.text.toString()
            val country2 = binding.etNation.text.toString()
            addFood(Food(food=food2, country=country2))
        } // 음식 및 나라 입력

        binding.btnUpdate.setOnClickListener {
            val name = binding.etFood.text.toString()
            val Country2 = binding.etNation.text.toString()
            modify(name, Country2)
        } // 음식 기준 나라이름 변경

        binding.btnDelete.setOnClickListener {
            val food = binding.etFood.text.toString()
            remove(food)
        } // 음식 기준 삭제
    }

    fun showAllFoods() {
        CoroutineScope(Dispatchers.IO).launch {
            val flowFoods = foodDao.getAllFoods()
            flowFoods.collect{foods ->
                for (food in foods) {
                    Log.d(TAG, food.toString())
                }
            }
        }
    }
    fun showFoodByCountry(country: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val foods = foodDao.getFoodByCountry(country)
            for (food in foods) {
                Log.d(TAG, food.toString())
            }
        }
    }

     fun addFood(food: Food) {
        CoroutineScope(IO).launch {
            foodDao.insertFood(food)
        }
    }

    fun modify(foodName: String, newCountry: String) {
        CoroutineScope(IO).launch {
            foodDao.updateFood(foodName, newCountry)
        }
    }

    fun remove(foodName: String) {
        CoroutineScope(IO).launch {
            foodDao.deleteFood(foodName)
        }
    }
}
