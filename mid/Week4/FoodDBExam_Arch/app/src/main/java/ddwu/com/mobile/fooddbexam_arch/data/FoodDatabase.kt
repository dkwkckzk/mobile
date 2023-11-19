package ddwu.com.mobile.fooddbexam_arch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Singleton 패턴 적용
@Database(entities = [Food::class], version=1)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao() : FoodDao

    companion object {
        @Volatile       // Main memory 에 저장한 값 사용
        private var INSTANCE : FoodDatabase? = null

        // INSTANCE 가 null 이 아니면 반환, null 이면 생성하여 반환
        fun getDatabase(context: Context) : FoodDatabase {
            return INSTANCE ?: synchronized(this) {     // 단일 스레드만 접근
                val instance = Room.databaseBuilder(context.applicationContext,
                    FoodDatabase::class.java, "food_db").build()
                INSTANCE = instance
                instance
            }
        }
    }

}




