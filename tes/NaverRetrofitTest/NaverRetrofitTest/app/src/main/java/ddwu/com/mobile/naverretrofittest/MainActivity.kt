package ddwu.com.mobile.naverretrofittest

import android.content.Intent
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

class MainActivity : AppCompatActivity() {

    val BASE_URL: String by lazy { getString(R.string.naver_api_url) }
    val CLIENT_ID: String by lazy { getString(R.string.client_id) }
    val CLIENT_SECRET: String by lazy { getString(R.string.client_secret) }

    private val TAG = "MainActivity"

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val adapter by lazy {
        BookAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        mainBinding.rvBooks.adapter = adapter
        mainBinding.rvBooks.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener(object : BookAdapter.OnItemClickListner {
            override fun onItemClick(view: View, position: Int) {
                val url = adapter.books?.get(position)?.image
                Glide.with(applicationContext)
                    .load(url)
                    .into(mainBinding.imageView)

                // DetailActivity 를 호출하며 image url 을 intent 로 전달
                val intent = Intent(applicationContext, DetailActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            }
        })


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(IBookAPIService::class.java)


        mainBinding.btnSearch.setOnClickListener {
            val keyword = mainBinding.etKeyword.text.toString()

            val apiCallback = object : Callback<BookRoot> {
                override fun onResponse(call: Call<BookRoot>, response: Response<BookRoot>) {
                    if (response.isSuccessful) {
                        val root: BookRoot? = response.body()
                        adapter.books = root?.items
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.d(TAG, "Unsuccessful Response")
                        Log.d(TAG, response.errorBody()!!.string())     // 응답 오류가 있을 때 상세정보 확인
                    }
                }

                override fun onFailure(call: Call<BookRoot>, t: Throwable) {
                    Log.d(TAG, "OpenAPI Call Failure ${t.message}")
                }
            }

            val apiCall: Call<BookRoot> = service.getBooksByKeyword(CLIENT_ID, CLIENT_SECRET, keyword)

            apiCall.enqueue(apiCallback)
        }
    }
}