package ddwu.com.mobile.fooddbexam_arch

import android.app.Application
import ddwu.com.mobile.fooddbexam_arch.data.FoodDatabase
import ddwu.com.mobile.fooddbexam_arch.data.FoodRepository

class FoodApplication : Application(){
    val database by lazy {
        FoodDatabase.getDatabase(this)

    }

    val repository by lazy {
        FoodRepository(database.foodDao())
    }
}