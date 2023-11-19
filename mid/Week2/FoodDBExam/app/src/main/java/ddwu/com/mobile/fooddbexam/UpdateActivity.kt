package ddwu.com.mobile.fooddbexam

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import ddwu.com.mobile.fooddbexam.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding: ActivityUpdateBinding
    lateinit var helper: FoodDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        helper = FoodDBHelper(this)

        val upId = updateBinding.etUpdateId
        val upFood = updateBinding.etUpdateFood
        val btnUp = updateBinding.btnUpdateFood
        val btnCancel = updateBinding.btnUpdateCancel

        btnUp.setOnClickListener {
            val id = upId.text.toString()
            val food = upFood.text.toString()
            modifyFood(id.toInt(), food)
            finish()  // 데이터 수정 후 액티비티 종료
        }

        btnCancel.setOnClickListener {
            finish()  // 취소 버튼 클릭시 액티비티 종료
        }
    }

    private fun modifyFood(id: Int, food: String) {
        val db = helper.writableDatabase
        val updateRow = ContentValues()

        updateRow.put("food", food)

        val whereClause="${BaseColumns._ID}=?"
        val whereArgs= arrayOf(id.toString())

        db.update("food_table" ,updateRow ,whereClause ,whereArgs )

        db.close()
    }
}
