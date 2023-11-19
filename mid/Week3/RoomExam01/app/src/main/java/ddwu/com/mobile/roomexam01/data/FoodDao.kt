package ddwu.com.mobile.roomexam01.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table2")
    fun getAllFoods() : Flow<List<Food>>

    @Query("SELECT * FROM food_table2 WHERE country = :country")
    suspend fun getFoodByCountry(country: String) : List<Food>

    @Insert
    suspend fun insertFood(vararg food : Food)

    @Query("UPDATE food_table2 SET country = :country WHERE food = :foodName")
    suspend fun updateFood(foodName: String, country: String)

    @Query("DELETE FROM food_table2 WHERE food = :foodName")
    suspend fun deleteFood(foodName: String)
}