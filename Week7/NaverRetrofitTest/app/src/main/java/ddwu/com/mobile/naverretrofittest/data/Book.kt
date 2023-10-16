package ddwu.com.mobile.naverretrofittest.data

data class BookRoot(
    val items: List<Item>,
)


data class Item(
    val title: String,
    val image: String,
    val author: String,
    val publisher: String,
)