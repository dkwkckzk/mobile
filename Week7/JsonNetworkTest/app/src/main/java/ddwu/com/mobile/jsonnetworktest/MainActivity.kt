package ddwu.com.mobile.jsonnetworktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ddwu.com.mobile.jsonnetworktest.data.BoxOfficeRoot
import ddwu.com.mobile.jsonnetworktest.databinding.ActivityMainBinding
import ddwu.com.mobile.jsonnetworktest.network.IBoxOfficeAPIService
import ddwu.com.mobile.jsonnetworktest.ui.MovieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    lateinit var mainBinding : ActivityMainBinding
    lateinit var adapter : MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        adapter = MovieAdapter()
        mainBinding.rvMovies.adapter = adapter
        mainBinding.rvMovies.layoutManager = LinearLayoutManager(this)


        val retrofit = Retrofit.Builder()
            .baseUrl(resources.getString(R.string.kobis_url))
            .addConverterFactory( GsonConverterFactory.create() )
            .build()

        val service = retrofit.create(IBoxOfficeAPIService::class.java) // 인터페이스를 보고 만들어서 반환하라.(파싱하는 기능)


        mainBinding.btnSearch.setOnClickListener {
            val targetDate = mainBinding.etDate.text.toString()

            val apiCallback = object: Callback<BoxOfficeRoot> {

                override fun onResponse(call: Call<BoxOfficeRoot>, response: Response<BoxOfficeRoot>) {
                    if (response.isSuccessful) {
                        val root : BoxOfficeRoot? = response.body()
                        adapter.movies = root?.boxOfficeResult?.movies
                        adapter.notifyDataSetChanged()

                    } else {
                        Log.d(TAG, "Unsuccessful Response")
                    }
                }

                override fun onFailure(call: Call<BoxOfficeRoot>, t: Throwable) {
                    Log.d(TAG, "OpenAPI Call Failure ${t.message}")
                }

            }

            val apiCall : Call<BoxOfficeRoot>
            = service.getDailyBoxOffice(
                    "json",
                    resources.getString(R.string.kobis_key),
                    targetDate )

            apiCall.enqueue(apiCallback)

        }


        val url = resources.getString(R.string.image_url)
        Glide.with(this)
            .load(url)
            .into(mainBinding.imageView)
    }
}