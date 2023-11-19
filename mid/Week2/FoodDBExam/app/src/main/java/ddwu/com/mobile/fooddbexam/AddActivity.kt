package ddwu.com.mobile.fooddbexam

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import ddwu.com.mobile.fooddbexam.databinding.ActivityAddBinding
import ddwu.com.mobile.fooddbexam.databinding.ActivityUpdateBinding

class AddActivity : AppCompatActivity() {

    lateinit var addBinding : ActivityAddBinding
    lateinit var helper : FoodDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        helper = FoodDBHelper(this)  // 'var' 키워드 제거

        val AddFood = addBinding.etAddFood
        val AddCountry = addBinding.etAddNation
        val btnAdd = addBinding.btnAddFood
        val btnCancel = addBinding.btnAddCancel

        btnAdd.setOnClickListener {
            val food = AddFood.text.toString()
            val country = AddCountry.text.toString()
            addFood(food, country)  // 함수 호출 추가
            finish()  // 데이터 추가 후 액티비티 종료
        }

        btnCancel.setOnClickListener {
            finish()  // 취소 버튼 클릭시 액티비티 종료
        }
    }

    fun addFood(food: String, country: String) {
        val db = helper.writableDatabase
        val newRow = ContentValues()
        newRow.put("food", food)
        newRow.put("country", country)
        db.insert("food_table", null, newRow)

        db.close()  // 수정된 부분 (helper 대신에 db.close() 호출)

    }
}


//추가 버튼 클릭시 -> 데이터베이스에 반영
//취소 버튼 클릭시 -> 데이터ㅔ이스에 미반영
