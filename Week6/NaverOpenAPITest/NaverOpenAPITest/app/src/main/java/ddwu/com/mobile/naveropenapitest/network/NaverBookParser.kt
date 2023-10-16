package ddwu.com.mobile.naveropenapitest.network

import android.util.Xml
import ddwu.com.mobile.naveropenapitest.data.Book
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class NaverBookParser {
    private val ns: String? = null

    companion object {
        val FAULT_RESULT = "faultResult"
        val CHANNEL_TAG = "channel"
        val TITL_TAG = "title"
        val AUTHOR_TAG = "author"
        val PUBLISHER_TAG = "publisher"
    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream?) : List<Book> {

        inputStream.use { inputStream ->
            val parser : XmlPullParser = Xml.newPullParser()

            /*Parser 의 동작 정의, next() 호출 전 반드시 호출 필요*/
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)

            /* Paring 대상이 되는 inputStream 설정 */
            parser.setInput(inputStream, null)

            /*Parsing 대상 태그의 상위 태그까지 이동*/
            whie(parser.name != "channel") {
                parser.next()
            }
            return readBookList(parser)
        }
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readBookList(parser: XmlPullParser) : List<Book> {
        val books = mutableListOf<Book>()

        parser.require(XmlPullParser.START_TAG, ns, "channel")
        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == CHANNEL_TAG) { // ③ 맞는 항목의 태그인지 검사하고
                books.add( readDailyBoxOffice(parser) ) // 내부에 있는 TAG를 파싱하는 함수 호출
            } else {
                skip(parser)
            }
        }
        return books
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readBookItem(parser: XmlPullParser) : Book {
        parser.require(XmlPullParser.START_TAG, ns, DAILY_BOXOFFICE_TAG)
        var title : String? = null
        var author : String? = null
        var publisher : String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) { // ④ 내가 관심있는 TAG를 만나면 값을 읽는 작업
                TITLE_TAG -> title = readTextInTag(parser, TITLE_TAG) // 찾으면 또 세부에서 파싱
                AUTHOR_TAG -> author = readTextInTag(parser, AUTHOR_TAG)
                PUBLISHER_TAG -> publisher = readTextInTag(parser, PUBLISHER_TAG)
                else -> skip(parser)
            }
        }
        return Book (title, author, publisher) // ⑤ DTO로 읽어 반환

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