package ddwu.com.mobile.naverretrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ddwu.com.mobile.naverretrofittest.data.BookRoot
import ddwu.com.mobile.naverretrofittest.databinding.ActivityMainBinding
import ddwu.com.mobile.naverretrofittest.network.IBookAPIService
import ddwu.com.mobile.naverretrofittest.ui.BookAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

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
                val url = adapter.books?.get(position)?.image
                Glide.with(view.context)
                    .load(url)
                    .into(mainBinding.imageView)
                // RecyclerView 항목 클릭 시 해당 위치의 Item 이 갖고 있는 image 를 Glide 에 전달
            }
        })


        val retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(IBookAPIService::class.java) // retrofit을 사용해서 내가만든 인터페이스를 만들겠다.(자동으로)


        mainBinding.btnSearch.setOnClickListener {
            val keyword = mainBinding.etKeyword.text.toString()

//            val apiCallback = object: Callback<BookRoot> {
//                override fun onResponse(call: Call<BookRoot>, response: Response<BookRoot>) {
//                    if (response.isSuccessful) {
//                        val root : BookRoot? = response.body()
//                        adapter.books = root?.items
//                        adapter.notifyDataSetChanged()
//                    }
//                    else {
//                        Log.d(TAG, "Unsuccessful Response")
//                    }
//                }
//
//                override fun onFailure(call: Call<BookRoot>, t: Throwable) {
//                    Log.d(TAG, "OpenAPI Call Failure ${t.message}")
//                }
//            }

            val apiCall : Call<BookRoot>
            = service.getBooksByKeyword(
                resources.getString(R.string.client_id),
                resources.getString(R.string.client_secret),
                keyword,
            ) // 인터페이스 값 삽입 후 호출 받아 오기

            apiCall.enqueue( // 인터페이스를 처리하는 callback 함수
                object: Callback<BookRoot> {
                    override fun onResponse(call: Call<BookRoot>, response: Response<BookRoot>) {
                        if (response.isSuccessful) {
                            val root : BookRoot? = response.body()
                            root?.items
                            adapter.books = root?.items
                            adapter.notifyDataSetChanged()
                        } // 여기까지만 해도 완성
                        else {
                            Log.d(TAG, "Unsuccessful Response")
                        }
                    }

                    override fun onFailure(call: Call<BookRoot>, t: Throwable) {
                        Log.d(TAG, "OpenAPI Call Failure ${t.message}")
                    }
                }
            )

        }

//        val url = resources.getString(R.string.image_url)
//        Glide.with(this)
//            .load(url)
//            .into(mainBinding.imageView)
    }
}

// 리사이클러 뷰 롱클릭 한번 보기