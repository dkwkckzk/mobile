package ddwu.com.mobile.openapitest.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import ddwu.com.mobile.openapitest.R
import ddwu.com.mobile.openapitest.data.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import javax.net.ssl.HttpsURLConnection
import kotlin.jvm.Throws


class NetworkManager(val context: Context) {
    private val TAG = "NetworkManager"

    val openApiUrl by lazy {
        context.resources.getString(R.string.kobis_url) /* 키 발급받아서 채우기*/
    }

    @Throws(IOException::class)
    fun downloadXml(date: String) : List<Movie>? {
        var movies : List<Movie>? = null

        val inputStream = downloadUrl( openApiUrl + date) /*url 주소 넘김*/

        /*Parser 생성 및 parsing 수행*/
        val parser = BoxOfficeParser()
        movies = parser.parse(inputStream)

        return movies
    }


    @Throws(IOException::class)
    private fun downloadUrl(urlString: String) : InputStream? {
        val url = URL(urlString) /*url 주소 받아서 url 만듦*/
        return (url.openConnection() as? HttpURLConnection)?.run {
            readTimeout = 5000
            connectTimeout = 5000
            requestMethod = "GET"
            doInput = true
            connect()
            inputStream // 파서한테 inputStream 전달
        }
    }


    // InputStream 을 String 으로 변환
    private fun readStreamToString(iStream : InputStream?) : String {
        val resultBuilder = StringBuilder()

        val inputStreamReader = InputStreamReader(iStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        var readLine : String? = bufferedReader.readLine()
        while (readLine != null) {
            resultBuilder.append(readLine + System.lineSeparator())
            readLine = bufferedReader.readLine()
        }

        bufferedReader.close()
        return resultBuilder.toString()
    }


    // InputStream 을 Bitmap 으로 변환
    private fun readStreamToImage(iStream: InputStream?) : Bitmap {
        val bitmap = BitmapFactory.decodeStream(iStream)
        return bitmap
    }

}