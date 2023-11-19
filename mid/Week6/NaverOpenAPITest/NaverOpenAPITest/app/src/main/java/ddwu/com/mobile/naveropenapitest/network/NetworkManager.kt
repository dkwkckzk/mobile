package ddwu.com.mobile.openapitest.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import ddwu.com.mobile.naveropenapitest.R
import ddwu.com.mobile.naveropenapitest.data.Book
import ddwu.com.mobile.naveropenapitest.network.NaverBookParser
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
        /* Resource 의 strings.xml 에서 필요한 정보를 읽어옴 */
        context.resources.getString(R.string.naver_url)
    }

    @Throws(IOException::class)
    fun downloadXml(keyword: String) : List<Book>? {
        var movies : List<Book>? = null

        val inputStream = downloadUrl( openApiUrl + keyword)

        /*Parser 생성 및 parsing 수행*/
        val parser = NaverBookParser()
        movies = parser.parse(inputStream)

        return movies
    }

    @Throws(IOException::class)
    private fun downloadUrl(urlString: String) : InputStream? {
        val url = URL(urlString)

        /* Resource 의 strings.xml 에서 필요한 정보를 읽어옴 */
        val cliedId = context.resources.getString(R.string.client_id)
        val clientSecret = context.resources.getString(R.string.client_secret)

        return (url.openConnection() as? HttpURLConnection)?.run {
            readTimeout = 5000
            connectTimeout = 5000
            requestMethod = "GET"
            doInput = true

            /*Naver ClientID/Secret 을 HTTP Header Property에 저장*/
            setRequestProperty("X-Naver-Client-Id", cliedId)
            setRequestProperty("X-Naver-Client-Secret", clientSecret)

            connect()
            inputStream
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