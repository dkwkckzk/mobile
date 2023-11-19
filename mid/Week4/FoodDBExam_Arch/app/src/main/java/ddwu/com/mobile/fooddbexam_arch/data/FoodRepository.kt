package ddwu.com.mobile.fooddbexam_arch.data

import ddwu.com.mobile.fooddbexam_arch.data.Food
import ddwu.com.mobile.fooddbexam_arch.data.FoodDao
import kotlinx.coroutines.flow.Flow // 코루틴의 Flow 추가해야 됨 주의!!

class FoodRepository(private val foodDao: FoodDao) {
    val allFoods : Flow<List<Food>> = foodDao.getAllFoods()

    suspend fun addFood(food: Food) {
        foodDao.insertFood(food)
    }

    suspend fun deleteFood(food: String) {
        foodDao.deleteFood(food)
    }
}