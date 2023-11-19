package ddwu.com.mobile.fooddbexam

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import ddwu.com.mobile.fooddbexam.databinding.ActivityMainBinding
import android.util.Log
import java.util.Currency

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var binding : ActivityMainBinding

    lateinit var helper : FoodDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helper = FoodDBHelper(this)

        binding.btnSelect.setOnClickListener{
            showFoods()

        }

        binding.btnAdd.setOnClickListener{
            //addFood("햄버거", "미국")
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)

        }

        binding.btnUpdate.setOnClickListener{
            //modifyFood()
            val intent = Intent(this, UpdateActivity::class.java)
            startActivity(intent)
        }

        binding.btnRemove.setOnClickListener{
            //deleteFood()
            val intent = Intent(this, RemoveActivity::class.java)
            startActivity(intent)
        }

    }

/*    fun addFood(food: String, country: String) {
        val db = helper.writableDatabase
        val newRow = ContentValues()
        newRow.put("food", food)
        newRow.put("country", country)
        db.insert("food_table", null, newRow)

        helper.close()

    }*/

/*    fun modifyFood() {
        val db = helper.writableDatabase
        val updateRow = ContentValues()

        updateRow.put("country", "한국")

        val whereClause="${FoodDBHelper.COL_FOOD}=?"
        val whereArgs= arrayOf("카레")

        db.update("food_table" ,updateRow ,whereClause ,whereArgs )

        helper.close()
    }*/

/*    fun deleteFood() {
        val db=helper.writableDatabase

        val whereClause="${FoodDBHelper.COL_FOOD}=?"
        val whereArgs= arrayOf("카레")

        db.delete("food_table" ,whereClause ,whereArgs )

        helper.close()
    }*/

    @SuppressLint("Range")
    fun showFoods() { // select
        val list = ArrayList<FoodDto>()
        val db = helper.readableDatabase
        val columns = null
        val selection = null
        val selectArgs =null
        val cursor = db.query("food_table", columns, selection, selectArgs, null, null,null)

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val food = getString(getColumnIndex("food"))
                val country = getString(getColumnIndex("country"))
                list.add(FoodDto(id, food, country))
            }
            close()
        }

        helper.close()

        var data = ""

        for (dto in list) {
            data += dto.toString() + "\n"
        }

        binding.tvDisplay.text=data // Update the text of the TextView with the string 'data'
    }
}