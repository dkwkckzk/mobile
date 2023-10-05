package ddwu.com.mobile.fooddbexam.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAllFoods() : Flow<List<Food>> // 계속 감시할거임

    @Query("SELECT * FROM food_table WHERE country= :country") // 나라에 해당하는 음식명 출력
    fun getFoodByCountry(country: String) : Flow<List<Food>>

    @Insert
    suspend fun insertFood(vararg food : Food) // 정말 손이 빠른 요리사가 동시에 여러 요리 가능

    @Update
    suspend fun updateFood(food : Food) // 멈춰서 다른 작업 수행 후 다시 수행 가능

    @Delete
    suspend fun deleteFood(food : Food) // 멈출수 있는 자격 부여
}

