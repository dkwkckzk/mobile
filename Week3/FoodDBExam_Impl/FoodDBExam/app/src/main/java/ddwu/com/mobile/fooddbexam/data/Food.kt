package ddwu.com.mobile.fooddbexam.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity (tableName = "food_table")
data class Food (
    @PrimaryKey (autoGenerate = true)
    val _id: Int,

    var food: String?,

    var country: String?,
)
{
    // data class 는 toString() 이 자동생성되므로 생략 가능
    override fun toString() = "$_id - $food ( $country )\n"
}

