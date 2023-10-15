package ddwu.com.mobile.openapitest.network

import android.util.Xml
import ddwu.com.mobile.openapitest.data.Movie
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class BoxOfficeParser {
    private val ns: String? = null

    companion object { // ① 어떤 TAG를 잘라낼지 함수로 정함
        val FAULT_RESULT = "faultResult"    // OpenAPI 결과에 오류가 있을 때에 생성하는 정보를 위해 지정
        val DAILY_BOXOFFICE_TAG = "dailyBoxOffice"
        val RANK_TAG = "rank"
        val TITLE_TAG = "movieNm"
        val OPEN_DATE_TAG = "openDt"
    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream?) : List<Movie> {

        inputStream.use { inputStream ->
            val parser : XmlPullParser = Xml.newPullParser()

            /*Parser 의 동작 정의, next() 호출 전 반드시 호출 필요*/
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)

            /* Paring 대상이 되는 inputStream 설정 */
            parser.setInput(inputStream, null)

            /*Parsing 대상 태그의 상위 태그까지 이동*/
            while (parser.name != "dailyBoxOfficeList") { // ② 파싱하는 내용 중에 내가 잘라 내려고 하는 태그의 윗부분 태그까지이동
                parser.next()
            }

            return readBoxOffice(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readBoxOffice(parser: XmlPullParser) : List<Movie> {
        val movies = mutableListOf<Movie>()

        parser.require(XmlPullParser.START_TAG, ns, "dailyBoxOfficeList")
        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == DAILY_BOXOFFICE_TAG) { // ③ 맞는 항목의 태그인지 검사하고
                movies.add( readDailyBoxOffice(parser) ) // 내부에 있는 TAG를 파싱하는 함수 호출
            } else {
                skip(parser)
            }
        }

        return movies
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readDailyBoxOffice(parser: XmlPullParser) : Movie {
        parser.require(XmlPullParser.START_TAG, ns, DAILY_BOXOFFICE_TAG)
        var rank : Int? = null
        var title : String? = null
        var openDate: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) { // ④ 내가 관심있는 TAG를 만나면 값을 읽는 작업
                RANK_TAG -> rank = readTextInTag(parser, RANK_TAG).toInt()
                TITLE_TAG -> title = readTextInTag(parser, TITLE_TAG) // 찾으면 또 세부에서 파싱
                OPEN_DATE_TAG -> openDate = readTextInTag(parser, OPEN_DATE_TAG)
                else -> skip(parser)
            }
        }
        return Movie (rank, title, openDate) // ⑤ DTO로 읽어 반환
    }


    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTextInTag (parser: XmlPullParser, tag: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tag)
        var text = ""
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.text
            parser.nextTag()
        }
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return text
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

}