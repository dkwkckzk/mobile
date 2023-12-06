package ddwu.com.mobile.naverretrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.naverretrofittest.databinding.ActivityMainBinding
import ddwu.com.mobile.naverretrofittest.ui.BookAdapter

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    lateinit var mainBinding : ActivityMainBinding
    lateinit var adapter : BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        adapter = BookAdapter()
        mainBinding.rvBooks.adapter = adapter
        mainBinding.rvBooks.layoutManager = LinearLayoutManager(this)


        adapter.setOnItemClickListener(object : BookAdapter.OnItemClickListner {
            override fun onItemClick(view: View, position: Int) {
                // RecyclerView 항목 클릭 시 해당 위치의 Item 이 갖고 있는 image 를 Glide 에 전달
            }
        })


        val retrofit =

        val service =


        mainBinding.btnSearch.setOnClickListener {
            val keyword = mainBinding.etKeyword.text.toString()


        }
    }
}