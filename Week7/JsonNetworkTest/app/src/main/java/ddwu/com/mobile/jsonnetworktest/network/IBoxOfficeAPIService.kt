package ddwu.com.mobile.jsonnetworktest.network

import ddwu.com.mobile.jsonnetworktest.data.BoxOfficeRoot
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IBoxOfficeAPIService {
    @GET("kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.{type}")
    fun getDailyBoxOffice(
        @Path("type") type: String,
        @Query("key") key: String,
        @Query ("targetDt") targetDate: String,
    )
    : Call<BoxOfficeRoot>
}

