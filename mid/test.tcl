@Entity
(tableName = "food_table2")
data class Food(
    @PrimaryKey(autoGenerate = true)
    var _id: Int? = null,
    var food: String,
    var country: String
) {
    override fun toString(): String {
        return "$_id - $food ($country)"
    }
}