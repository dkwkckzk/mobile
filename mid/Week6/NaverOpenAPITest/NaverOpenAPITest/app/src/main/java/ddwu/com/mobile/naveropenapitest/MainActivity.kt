package ddwu.com.mobile.naveropenapitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.naveropenapitest.data.Book
import ddwu.com.mobile.naveropenapitest.databinding.ActivityMainBinding
import ddwu.com.mobile.openapitest.network.NetworkManager
import ddwu.com.mobile.openapitest.ui.MovieAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    lateinit var mainBinding : ActivityMainBinding
    lateinit var adapter : MovieAdapter
    lateinit var networkDao : NetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        networkDao = NetworkManager(this)

        adapter = MovieAdapter()
        mainBinding.rvMovies.adapter = adapter
        mainBinding.rvMovies.layoutManager = LinearLayoutManager(this)

        mainBinding.btnSearch.setOnClickListener {
            val keyword = mainBinding.etKeyword.text.toString()

            CoroutineScope(Dispatchers.Main).launch{
                val def = async(Dispatchers.IO) {
                    var books : List<Book>? = null
                    try {
                        books = networkDao.downloadXml(keyword)
                    } catch (e: IOException) {
                        Log.d(TAG, e.message?: "null")
                        null
                    } catch (e: XmlPullParserException) {
                        Log.d(TAG, e.message?: "null")
                        null
                    }
                    books
                }

                adapter.books = def.await()
                adapter.notifyDataSetChanged()
            }
        }

    }
}