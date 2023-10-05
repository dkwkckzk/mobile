package ddwu.com.mobile.fooddbexam_arch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAllFoods() : Flow<List<Food>>

    @Query("SELECT * FROM food_table WHERE country = :country")
    suspend fun getFoodByCountry(country: String) : List<Food>

    @Insert
    suspend fun insertFood(vararg food : Food)

    @Query("UPDATE food_table SET country = :country WHERE food = :food")
    suspend fun updateFood(food : String, country: String)

    @Query("DELETE FROM food_table WHERE food = :food")
    suspend fun deleteFood(food : String)
}