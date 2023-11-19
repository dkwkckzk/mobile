package ddwu.com.mobile.fooddbexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import ddwu.com.mobile.fooddbexam.data.Food
import ddwu.com.mobile.fooddbexam.data.FoodDao
import ddwu.com.mobile.fooddbexam.data.FoodDatabase
import ddwu.com.mobile.fooddbexam.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var binding : ActivityMainBinding // View Binding 객체 선언
    lateinit var db : FoodDatabase // Room Database 객체 선언
    lateinit var foodDao : FoodDao // Data Access Object 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // View Binding 초기화
        setContentView(binding.root)

        // Room Database 생성
        val db : FoodDatabase = Room.databaseBuilder(
            applicationContext, FoodDatabase::class.java, "food_db"
        ).build()

        foodDao = db.foodDao()  // DAO 초기화

        binding.btnSelect.setOnClickListener{
            showFoodByCountry("중국")  // 중국 요리 정보 출력 요청
        }

        binding.btnAdd.setOnClickListener{
            addFood( Food(0, "샐러드", "미국") )  // 미국 요리 정보 추가 요청
        }

        binding.btnUpdate.setOnClickListener{
            modifyFood( Food(1, "김치찌개", "한국") )  // 한국 요리 정보 수정 요청
        }


        binding.btnRemove.setOnClickListener{
            removeFood( Food(2, null, null) )  // 특정 음식 삭제 요청 (id가 2인 음식)
        }
    }


    /*각 함수 내부에서 적절한 DAO 호출*/

    fun addFood(food: Food) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDao.insertFood(food)  // 백그라운드 스레드에서 음식 정보 추가 수행
        }
    }

    fun modifyFood(food: Food) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDao.updateFood(food)  // 백그라운드 스레드에서 음식 정보 수정 수행
        }
    }


    fun removeFood(food: Food) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDao.deleteFood(food)  // 백그라운드 스레드에서 음식 정보 삭제 수행
        }
    }

    fun showFoodByCountry(country: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val flowFood = foodDao.getFoodByCountry(country)
            flowFood.distinctUntilChanged().collect { foods ->
                var data = ""
                for (food in foods) {
                    data += food.toString() + ""
                }

                withContext(Dispatchers.Main){
                    binding.tvDisplay.text = data
                    /* 메인 스레드에서 TextView 업데이트 수행 */
                }
            }
        }
    }

    /* 로그에 모든 음식 데이터 출력하는 함수 */
    fun showAllFoods() {
        CoroutineScope(Dispatchers.IO).launch {
            val flowFood = foodDao.getAllFoods()
            flowFood.distinctUntilChanged().collect{
                for(food in it) {
                    Log.d(TAG, food.toString())
                }
            } // 자동으로 계속 실행되어 계속 감시하고 있다.
        } // 이 안에서 사용가능(collect) -> 코 루틴 형식
    }
}
