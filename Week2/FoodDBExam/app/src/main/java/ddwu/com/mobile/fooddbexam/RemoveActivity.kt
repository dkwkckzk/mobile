package ddwu.com.mobile.fooddbexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwu.com.mobile.fooddbexam.databinding.ActivityRemoveBinding

class RemoveActivity : AppCompatActivity() {

    lateinit var helper : FoodDBHelper

    lateinit var removeBinding : ActivityRemoveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        removeBinding = ActivityRemoveBinding.inflate(layoutInflater)
        setContentView(removeBinding.root)

        helper = FoodDBHelper(this)

        val reFood = removeBinding.etRemoveFood
        val btnUp = removeBinding.btnRemoveFood
        val btnCancel = removeBinding.btnRemoveCancel

        btnUp.setOnClickListener {
            val food = reFood.text.toString()
            deleteFood(food)
            finish()  // 데이터 수정 후 액티비티 종료
        }

        btnCancel.setOnClickListener {
            finish()  // 취소 버튼 클릭시 액티비티 종료
        }
    }

    fun deleteFood(food: String) {
        val db=helper.writableDatabase

        val whereClause="food=?"
        val whereArgs= arrayOf(food)

        db.delete("food_table" ,whereClause ,whereArgs )

        helper.close()
    }
}